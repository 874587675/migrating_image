package com.dfg.java_template.business.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfg.java_template.business.entity.User;
import com.dfg.java_template.business.mapper.UserMapper;
import com.dfg.java_template.business.param.convertor.UserConvertor;
import com.dfg.java_template.business.param.page.UserPageParam;
import com.dfg.java_template.business.param.page.vo.PageVO;
import com.dfg.java_template.business.param.query.UserQueryParam;
import com.dfg.java_template.business.param.remove.RemoveBaseParam;
import com.dfg.java_template.business.param.save.UserSaveParam;
import com.dfg.java_template.business.param.update.UserUpdateParam;
import com.dfg.java_template.business.param.vo.UserVO;
import com.dfg.java_template.business.service.UserService;
import com.dfg.java_template.common.constant.CacheConstants;
import com.dfg.java_template.common.constant.CommonConstants;
import com.dfg.java_template.common.constant.StatusEnum;
import com.dfg.java_template.common.exception.ServiceException;
import com.dfg.java_template.common.exception.ServiceExceptionEnum;
import com.dfg.java_template.common.exception.token.TokenException;
import com.dfg.java_template.common.exception.token.TokenExceptionEnum;
import com.dfg.java_template.framework.redis.RedisCache;
import com.dfg.java_template.framework.security.constant.LoginRoleEnum;
import com.dfg.java_template.framework.security.constant.TokenTypeEnum;
import com.dfg.java_template.framework.security.core.AuthenticationContextHolder;
import com.dfg.java_template.framework.security.param.*;
import com.dfg.java_template.framework.security.service.login.FrontendUserDetailsService;
import com.dfg.java_template.framework.security.service.token.TokenService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zgc
 * @since 2025-07-01 15:18:53
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource(name = "frontendAuthenticationManager")
    private AuthenticationManager frontendAuthenticationManager;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private WxMaService wxMaService;

    @Resource
    private FrontendUserDetailsService detailsService;

    /**
     * 保存用户信息
     *
     * @param userSaveParam 保存用户信息参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserSaveParam userSaveParam) {
        User user = UserConvertor.SAVE.saveParamToEntity(userSaveParam);
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userMapper.insert(user);
    }

    /**
     * 修改用户信息
     *
     * @param userUpdateParam 修改用户信息参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateParam userUpdateParam) {
        userMapper.updateById(UserConvertor.UPDATE.updateParamToEntity(userUpdateParam));
    }

    /**
     * 删除用户信息属性
     *
     * @param removeBaseParam 删除用户信息属性参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUser(RemoveBaseParam removeBaseParam) {
        userMapper.deleteById(removeBaseParam.getSerializableId());
    }

    /**
     * 前台用户查询用户信息详情
     *
     * @return 用户信息VO
     */
    @Override
    public UserVO frontQueryUser() {
        String frontUserId = AuthenticationContextHolder.getFrontUserId();
        return UserConvertor.QUERY.entityToVo(userMapper.selectById(frontUserId));
    }

    /**
     * 前台用户登录
     *
     * @param loginBody 登录参数
     * @return 登录成功返回token
     */
    @Override
    public LoginVO loginUser(LoginBody loginBody, HttpServletRequest httpServletRequest) {
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();

        // 这里的AuthenticationContextHolder.setContext(authenticationToken)是为了在后续的操作中可以获取到当前登录用户的信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        AuthenticationContextHolder.setFrontContext(authenticationToken);
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = frontendAuthenticationManager.authenticate(authenticationToken);

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        String userId = loginUser.getUserId();
        String remoteAddr = httpServletRequest.getRemoteAddr();
        //更新最后登录时间
        userMapper.update(new LambdaUpdateWrapper<User>().eq(User::getUserId, userId).set(User::getLastLoginTime, new Date()).set(User::getLastIpAddress, remoteAddr));

        User user = userMapper.selectById(userId);
        return getLoginVO(username, remoteAddr, user);
    }

    private Map<String, String> getTokens(String userId, String username, String remoteAddr, LoginUser loginUser) {
        // 生成双token
        Map<String, Object> accessTokenClaims = new HashMap<>();
        accessTokenClaims.put("userId", userId);
        accessTokenClaims.put("username", username);
        accessTokenClaims.put("role", LoginRoleEnum.FRONT_ROLE.getCode());
        accessTokenClaims.put("tokenType", CacheConstants.ACCESS_TOKEN);
        accessTokenClaims.put("ipAddress", remoteAddr);

        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put("userId", userId);
        refreshTokenClaims.put("username", username);
        refreshTokenClaims.put("role", LoginRoleEnum.FRONT_ROLE.getCode());
        refreshTokenClaims.put("tokenType", CacheConstants.REFRESH_TOKEN);
        refreshTokenClaims.put("ipAddress", remoteAddr);

        TokenService.TokenParam tokenParam = tokenService.getTokenParam();

        int accessExpireTime = tokenParam.getAccessExpireTime(); // 访问token过期时间（分钟）
        int refreshExpireTime = tokenParam.getRefreshExpireTime(); // 刷新token过期时间（分钟）

        String accessToken = tokenService.generateToken(userId, accessTokenClaims, accessExpireTime, CacheConstants.ACCESS_TOKEN);
        String refreshToken = tokenService.generateToken(userId, refreshTokenClaims, refreshExpireTime, CacheConstants.REFRESH_TOKEN);

        // 保存用户信息到Redis（使用refresh token的过期时间）
        redisCache.setCacheObject(CacheConstants.LOGIN_USER + CacheConstants.FRONT_KEY + userId, loginUser, refreshExpireTime, TimeUnit.MINUTES);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put(TokenTypeEnum.ACCESS_TOKEN.getDesc(), accessToken);
        tokenMap.put(TokenTypeEnum.REFRESH_TOKEN.getDesc(), refreshToken);
        return tokenMap;
    }

    /**
     * 前台用户退出登录
     *
     * @return 退出登录成功
     */
    @Override
    public String exitUser() {
        String frontUserId = AuthenticationContextHolder.getFrontUserId();
        //删除token信息
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.FRONT_KEY + CacheConstants.ACCESS_TOKEN + frontUserId);
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_USER + CacheConstants.FRONT_KEY + CacheConstants.REFRESH_TOKEN + frontUserId);
        //删除登录用户信息
        redisCache.deleteObject(CacheConstants.LOGIN_USER + CacheConstants.FRONT_KEY + frontUserId);
        return "退出登录";
    }

    /**
     * 微信小程序登录
     *
     * @param wxLoginBody 微信登录参数
     * @return 微信登录成功返回token
     */
    @Override
    public LoginVO wxMiniLoginByPhone(WxLoginByPhoneBody wxLoginBody, HttpServletRequest httpServletRequest) {
        String code = wxLoginBody.getCode();
        String phoneCode = wxLoginBody.getPhoneCode();
        LoginVO loginVO = null;
        try {
            String appid = wxMaService.getWxMaConfig().getAppid();
            WxMaPhoneNumberInfo phoneInfo = detailsService.getPhoneNumber(appid, phoneCode);
            String phoneNumber = phoneInfo.getPhoneNumber();

            WxMaJscode2SessionResult sessionInfo = detailsService.getSessionInfo(code);
            String openid = sessionInfo.getOpenid();
            String remoteAddr = httpServletRequest.getRemoteAddr();
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openid));
            if (ObjectUtil.isEmpty(user)) {
                loginVO = constructUserVO(openid, phoneNumber, remoteAddr);
            } else {
                loginVO = getLoginVO(phoneNumber, remoteAddr, user);
            }
        } catch (ServiceException e) {
            throw ServiceExceptionEnum.GET_PHONE_ERROR.toException();
        }
        return loginVO;
    }

    private LoginVO constructUserVO(String openid, String phoneNumber, String remoteAddr) {
        User user = User.builder()
                .userName(phoneNumber)
                .openId(openid)
                .nickName(phoneNumber)
                .status(StatusEnum.ENABLE.getCode())
                .lastIpAddress(remoteAddr)
                .lastLoginTime(new Date()).build();

        userMapper.insert(user);

        return getLoginVO(phoneNumber, remoteAddr, user);
    }

    private LoginVO getLoginVO(String phoneNumber, String remoteAddr, User user) {
        LoginUser loginUser = LoginUser.builder()
                .userId(user.getUserId()).username(phoneNumber)
                .bCryptPassword(null)
                .status(user.getStatus())
                .roles(new ArrayList<>())  // 用户角色（如果前台用户页面需要角色）
                .permissions(new ArrayList<>()) // 用户权限（如果前台用户页面需要权限）
                .lastLoginAddress(remoteAddr).build();

        Map<String, String> tokens = getTokens(user.getUserId(), phoneNumber, remoteAddr, loginUser);

        String accessToken = tokens.get(TokenTypeEnum.ACCESS_TOKEN.getDesc());
        String refreshToken = tokens.get(TokenTypeEnum.REFRESH_TOKEN.getDesc());

        UserVO userVO = UserConvertor.QUERY.entityToVo(user);
        return LoginVO.builder().accessToken(accessToken).refreshToken(refreshToken).userVO(userVO).build();
    }

    /**
     * 刷新令牌
     *
     * @param request http请求
     */
    @Override
    public String refresh(HttpServletRequest request) {
        try {
            String refreshToken = request.getHeader(tokenService.getTokenParam().getRefreshToken());
            if (ObjectUtil.isNotEmpty(refreshToken)) {
                refreshToken = refreshToken.replace(CommonConstants.TOKEN_PREFIX, "");
            }
            log.info("refresh_token:{}", refreshToken);
            Claims refreshClaims = tokenService.parseToken(refreshToken, TokenTypeEnum.REFRESH_TOKEN.getCode());
            Claims expiredClaims;
            try {
                String expiredToken = request.getHeader(tokenService.getTokenParam().getAccessToken());
                expiredToken = expiredToken.replace(CommonConstants.TOKEN_PREFIX, "");
                // 尝试解析过期的token来获取用户信息
                expiredClaims = tokenService.parseToken(expiredToken, TokenTypeEnum.ACCESS_TOKEN.getCode());
            } catch (TokenException ex) {
                // 如果无法解析过期的token，就使用refreshToken中的用户信息
                expiredClaims = refreshClaims;
                expiredClaims.put("tokenType", TokenTypeEnum.ACCESS_TOKEN.getDesc());
            }
            String userId = expiredClaims.get("userId", String.class);
            String role = expiredClaims.get("role", String.class);
            // 验证refreshToken中的用户信息是否匹配
            String refreshUserId = refreshClaims.get("userId", String.class);
            String refreshRole = refreshClaims.get("role", String.class);
            if (!userId.equals(refreshUserId) || !role.equals(refreshRole)) {
                throw TokenExceptionEnum.TOKEN_INVALID.toException();
            }
            // 重新生成accessToken
            return tokenService.generateToken(userId, expiredClaims, tokenService.getTokenParam().getAccessExpireTime(), CacheConstants.ACCESS_TOKEN);
        } catch (Exception e) {
            log.error("刷新token失败", e);
            throw TokenExceptionEnum.TOKEN_INVALID.toException();
        }
    }

    /**
     * 微信一键快捷登录
     *
     * @param wxLoginBody        微信登录参数
     * @param httpServletRequest 请求头
     * @return LoginVO
     */
    @Override
    public LoginVO wxLoginByQuick(WxLoginByCodeBody wxLoginBody, HttpServletRequest httpServletRequest) {
        String code = wxLoginBody.getCode();
        LoginVO loginVO;
        try {
            WxMaJscode2SessionResult sessionInfo = detailsService.getSessionInfo(code);
            String openid = sessionInfo.getOpenid();
            String remoteAddr = httpServletRequest.getRemoteAddr();
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openid));
            if (ObjectUtil.isEmpty(user)) {
                loginVO = constructUserVO(openid, null, remoteAddr);
            } else {
                loginVO = getLoginVO(null, remoteAddr, user);
            }
        } catch (ServiceException e) {
            throw ServiceExceptionEnum.GET_PHONE_ERROR.toException();
        }
        return loginVO;
    }


    /**
     * 后台用户查询前台用户信息详情
     *
     * @param userQueryParam 用户查询参数
     * @return 用户信息VO
     */
    @Override
    public UserVO backQueryUser(UserQueryParam userQueryParam) {
        String userId = userQueryParam.getUserId();
        return UserConvertor.QUERY.entityToVo(userMapper.selectById(userId));
    }

    @Override
    public PageVO<UserVO> backPageUser(UserPageParam userPageParam) {
        String userId = userPageParam.getUserId();
        Page<User> page = page(new Page<>(userPageParam.getPageNo(), userPageParam.getPageSize()),
                new LambdaQueryWrapper<User>()
                        .eq(ObjectUtil.isNotEmpty(userId), User::getUserId, userId)
                        .orderByDesc(User::getUpdateTime)
        );
        return PageVO.addPageData(page, UserConvertor.LIST.listEntityToListVO(page.getRecords()));
    }
}
