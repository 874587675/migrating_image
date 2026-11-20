package com.dfg.java_template.framework.security.filter;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.dfg.java_template.common.constant.CacheConstants;
import com.dfg.java_template.common.constant.CommonConstants;
import com.dfg.java_template.common.exception.base.BaseException;
import com.dfg.java_template.common.exception.token.TokenException;
import com.dfg.java_template.common.exception.token.TokenExceptionEnum;
import com.dfg.java_template.common.util.servlet.ServletUtils;
import com.dfg.java_template.common.web.AjaxResult;
import com.dfg.java_template.framework.redis.RedisCache;
import com.dfg.java_template.framework.security.constant.LoginRoleEnum;
import com.dfg.java_template.framework.security.constant.TokenTypeEnum;
import com.dfg.java_template.framework.security.core.AuthenticationContextHolder;
import com.dfg.java_template.framework.security.param.LoginUser;
import com.dfg.java_template.framework.security.service.token.TokenService;
import com.dfg.java_template.framework.security.util.SecurityUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource(name = "JJWTService")
    private TokenService tokenService;

    @Resource
    private RedisCache redisCache;

    @Override
    public void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        try {
            LoginUser loginUser = new LoginUser();
            if (!isVerifyRequest(request)) {
                chain.doFilter(request, response);
                return;
            }
            String token = request.getHeader(tokenService.getTokenParam().getAccessToken());
            if (ObjectUtil.isNotEmpty(token)) {
                token = token.replace(CommonConstants.TOKEN_PREFIX, "");
            }

            if (ObjectUtil.isEmpty(token)) {
                throw TokenExceptionEnum.TOKEN_REQUIRED.toException();
            }
            if (ObjectUtil.isEmpty(SecurityUtils.getAuthentication())) {
                try {
                    Claims claims = tokenService.parseToken(token, TokenTypeEnum.ACCESS_TOKEN.getCode());
                    String userId = claims.get("userId", String.class);
                    String role = claims.get("role", String.class);
                    String tokenType = claims.get("tokenType", String.class);
                    log.info("[userId]:{},[role]:{},[tokenType]:{}", userId, role, tokenType);
                    String redisFrontToken = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.FRONT_KEY + tokenType + userId);
                    String redisBackToken = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.BACK_KEY + tokenType + userId);

                    String refreshFrontToken = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.FRONT_KEY + CacheConstants.REFRESH_TOKEN + userId);
                    String refreshBackToken = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.BACK_KEY + CacheConstants.REFRESH_TOKEN + userId);

                    if (ObjectUtil.isEmpty(redisFrontToken) && ObjectUtil.isEmpty(redisBackToken)) {
                        if (ObjectUtil.isNotEmpty(refreshFrontToken) && ObjectUtil.isNotEmpty(refreshBackToken)) {
                            log.info("redis缓存{}失效", tokenType);
                            throw TokenExceptionEnum.TOKEN_INVALID.toException();
                        }
                        log.info("redis缓存{}过期", tokenType);
                        throw TokenExceptionEnum.TOKEN_EXPIRED.toException();

                    }
                    if (ObjectUtil.isNotEmpty(redisFrontToken) && !token.equals(redisFrontToken)) {
                        log.info("redis缓存中前台用户token不匹配");
                        throw TokenExceptionEnum.TOKEN_INVALID.toException();
                    }
                    if (ObjectUtil.isNotEmpty(redisBackToken) && !token.equals(redisBackToken)) {
                        log.info("redis缓存中后台用户token不匹配");
                        throw TokenExceptionEnum.TOKEN_INVALID.toException();
                    }
                    if (LoginRoleEnum.FRONT_ROLE.getCode().equals(role)) {
                        log.info("前台用户角色信息获取");
                        loginUser = redisCache.getCacheObject(CacheConstants.LOGIN_USER + CacheConstants.FRONT_KEY + userId);
                        AuthenticationContextHolder.setFrontUserId(userId);
                    }
                    if (LoginRoleEnum.BACK_ROLE.getCode().equals(role)) {
                        log.info("后台用户角色信息获取");
                        loginUser = redisCache.getCacheObject(CacheConstants.LOGIN_USER + CacheConstants.BACK_KEY + userId);
                        AuthenticationContextHolder.setBackUserId(userId);
                    }
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (TokenException e) {
                    log.error("错误码{}", e.getCode());
                    if (TokenExceptionEnum.TOKEN_EXPIRED.getCode().equals(e.getCode())) {
                        boolean refreshSuccess = handleExpiredAccessToken(token, request, response);
                        if (refreshSuccess) {
                            // 刷新成功，继续执行过滤器链
                            chain.doFilter(request, response);
                            return;
                        }
                    }
                    throw e;
                }
            }
            chain.doFilter(request, response);
        } catch (BaseException e) {
            log.error("\n认证失败\n路径: {} \n异常类型: {}\n原因: {}\n",
                    request.getRequestURI(),
                    e.getClass().getSimpleName(),
                    e.getMessage());
            ServletUtils.renderString(response, JSON.toJSONString(
                            new AjaxResult(e.getCode(), e.getMessage(), request.getRequestURI())
                    )
            );
        } finally {
            // 清理 ThreadLocal，防止线程复用导致数据串号
            AuthenticationContextHolder.clearFrontContext();
            AuthenticationContextHolder.clearBackContext();
            AuthenticationContextHolder.clearFrontUserId();
            AuthenticationContextHolder.clearBackUserId();
            SecurityContextHolder.clearContext();
        }
    }

    /**
     * 处理过期的accessToken，尝试使用refreshToken刷新
     */
    private Boolean handleExpiredAccessToken(String expiredToken, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 从cookie中获取refreshToken
            String refreshToken = tokenService.getRefreshTokenFromCookie(request);
            if (ObjectUtil.isEmpty(refreshToken)) {
                return false;
            }
            // 解析refreshToken
            Claims refreshClaims = tokenService.parseToken(refreshToken, TokenTypeEnum.REFRESH_TOKEN.getCode());
            Claims expiredClaims = tokenService.parseExpiredToken(expiredToken);
            ;
            String userId = expiredClaims.get("userId", String.class);
            String role = expiredClaims.get("role", String.class);
            // 验证refreshToken中的用户信息是否匹配
            String refreshUserId = refreshClaims.get("userId", String.class);
            String refreshRole = refreshClaims.get("role", String.class);
            if (!userId.equals(refreshUserId) || !role.equals(refreshRole)) {
                return false;
            }
            // 重新生成accessToken
            String newAccessToken = tokenService.generateToken(userId, expiredClaims, tokenService.getTokenParam().getAccessExpireTime(), CacheConstants.ACCESS_TOKEN);
            // 设置新的accessToken到响应头
            response.setHeader(tokenService.getTokenParam().getAccessToken(), newAccessToken);

            setAuthentication(request, userId, role);
            return true;
        } catch (Exception e) {
            log.error("刷新token失败", e);
            return false;
        }
    }

    /**
     * 设置认证信息
     */
    public void setAuthentication(HttpServletRequest request, String userId, String role) {
        LoginUser loginUser;
        if (LoginRoleEnum.FRONT_ROLE.getCode().equals(role)) {
            loginUser = redisCache.getCacheObject(CacheConstants.LOGIN_USER + CacheConstants.FRONT_KEY + userId);
            AuthenticationContextHolder.setFrontUserId(userId);
        } else {
            loginUser = redisCache.getCacheObject(CacheConstants.LOGIN_USER + CacheConstants.BACK_KEY + userId);
            AuthenticationContextHolder.setBackUserId(userId);
        }
        if (ObjectUtil.isNotEmpty(loginUser)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            log.warn("未找到用户信息，userId: {}", userId);
        }
    }

    private boolean isVerifyRequest(HttpServletRequest request) {
        PathMatcher pathMatcher = new AntPathMatcher();
        String uri = request.getRequestURI();
        String[] securePatterns = {
                "/**/front/**",  // 匹配任意层级的front路径
                "/**/back/**",   // 匹配任意层级的back路径
        };

        return Arrays.stream(securePatterns)
                .anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }
}
