package com.dfg.java_template.framework.security.service.token;

import com.dfg.java_template.common.constant.CacheConstants;
import com.dfg.java_template.common.constant.CommonConstants;
import com.dfg.java_template.common.exception.token.TokenExceptionEnum;
import com.dfg.java_template.framework.redis.RedisCache;
import com.dfg.java_template.framework.security.constant.LoginRoleEnum;
import com.dfg.java_template.framework.security.constant.TokenTypeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@ConfigurationProperties("token")
@Component("JJWTService")
@Data
@Slf4j
public class TokenService {
    @Resource
    private RedisCache redisCache;

    private TokenParam tokenParam;

    private Key key;

    private static final long MINUTE_MILLISECOND = 60 * 1000L; // 1分钟的毫秒数

    private static final long HOUR_MILLISECOND = 60 * MINUTE_MILLISECOND;

    /**
     * 初始化密钥
     */
    @PostConstruct
    public void init() {
        log.info("正在初始化JWT密钥==========>>");
        String secret = tokenParam.getSecret();
        if (secret.length() < 32) {
            throw new IllegalArgumentException("密钥长度不足，必须至少为 256 位。"); // 确保 secret 长度至少为 256 位（32 字节）
        }
        // 使用secret来生成密钥
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // 使用字节数组来生成密钥
        log.info("初始化JWT密钥成功==========>>");
    }

    /**
     * 从数据声明生成令牌
     *
     * @param userId        用户主键ID
     * @param claims        数据声明
     * @param expireMinutes 过期时间(分钟)
     * @return 令牌
     */
    public String generateToken(String userId, Map<String, Object> claims, int expireMinutes, String tokenType) {
        claims.put("tokenType", tokenType);
        claims.put("userId", userId);
        String compact = Jwts.builder()
                .setClaims(claims)     //自定义数据 注意 先设置 claim 在设置 subject ，要不然 setClaim会覆盖subject
                .setSubject(userId)  //标识token是给谁使用
                .setIssuedAt(new Date())   //token签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expireMinutes * MINUTE_MILLISECOND)) //token过期时间
                .signWith(key) //使用密钥进行签名
                .compact();
        String[] split = tokenType.split(":");
        log.info("生成{}令牌成功", split[0]);
        String role = (String) claims.get("role");
        if (LoginRoleEnum.FRONT_ROLE.getCode().equals(role)) {
            redisCache.setCacheObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.FRONT_KEY + tokenType + userId, compact, expireMinutes, TimeUnit.MINUTES);    //将令牌存入redis中
        } else if (LoginRoleEnum.BACK_ROLE.getCode().equals(role)) {
            redisCache.setCacheObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.BACK_KEY + tokenType + userId, compact, expireMinutes, TimeUnit.MINUTES);
        }
        return compact;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public Claims parseToken(String token, Integer tokenCode) {
        try {
            log.info("正在解析[{}]令牌,类型[{}]", token, tokenCode);
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            if (TokenTypeEnum.ACCESS_TOKEN.getCode().equals(tokenCode)) {
                log.error("access-token超时失效");
            } else {
                log.error("refresh-token超时失效");
            }
            throw TokenExceptionEnum.TOKEN_EXPIRED.toException();
        }
    }

    /**
     * 解析过期的 token，获取 claims（不校验过期）
     */
    public Claims parseExpiredToken(String token) {
        try {
            log.info("解析过期token");
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (Exception e) {
            throw TokenExceptionEnum.TOKEN_INVALID.toException();
        }
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        log.info("正在获取refresh-token");
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CommonConstants.REFRESH_TOKEN.equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    log.info("获取refresh-token成功");
                    break;
                }
            }
        }
        return refreshToken;
    }

    /**
     * 设置刷新令牌到Cookie
     *
     * @param response     HttpServletResponse
     * @param refreshToken 刷新令牌
     */
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie(CommonConstants.REFRESH_TOKEN, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // 生产环境设为true 只能通过 HTTPS 安全连接传输 ，开发环境可设为false 可以通过 HTTP 或 HTTPS 传输
        refreshTokenCookie.setPath("/"); //
        refreshTokenCookie.setMaxAge(tokenParam.getRefreshExpireTime() * 60); // 转换为秒
        //refreshTokenCookie.setDomain("localhost");
        response.addCookie(refreshTokenCookie);
    }

    /**
     * 清除刷新令牌Cookie
     *
     * @param response HttpServletResponse
     */
    public void clearAccessTokenCookie(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie(CommonConstants.REFRESH_TOKEN, null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        //accessTokenCookie.setDomain("localhost");
        response.addCookie(accessTokenCookie);
    }

    @Data
    public static class TokenParam {
        private String accessToken; //令牌自定义标识
        private String refreshToken; //令牌自定义标识
        private String secret; //令牌密钥
        private int accessExpireTime; //令牌过期时间 单位分钟
        private int refreshExpireTime; //令牌过期时间 单位分钟
    }

}
