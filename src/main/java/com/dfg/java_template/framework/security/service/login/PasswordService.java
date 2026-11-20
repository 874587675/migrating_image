package com.dfg.java_template.framework.security.service.login;

import cn.hutool.core.util.ObjectUtil;
import com.dfg.java_template.common.constant.CacheConstants;
import com.dfg.java_template.common.exception.user.UserExceptionEnum;
import com.dfg.java_template.framework.redis.RedisCache;
import com.dfg.java_template.framework.security.constant.LoginRoleEnum;
import com.dfg.java_template.framework.security.core.AuthenticationContextHolder;
import com.dfg.java_template.framework.security.param.ValidUser;
import com.dfg.java_template.framework.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class PasswordService {

    @Value("${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value("${user.password.lockTime}")
    private int lockTime;

    @Resource
    private RedisCache redisCache;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String role, String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + role + ":" + username;
    }

    /**
     * 校验前台用户密码
     *
     * @param validUser 用户密码信息
     */
    public void validatePassword(ValidUser validUser, String role) {
        // 获取当前用户的认证信息
        Authentication authentication = null;
        if (LoginRoleEnum.FRONT_ROLE.getCode().equals(role)) {
            authentication = AuthenticationContextHolder.getFrontContext();
        } else if (LoginRoleEnum.BACK_ROLE.getCode().equals(role)) {
            authentication = AuthenticationContextHolder.getBackContext();
        }
        if (ObjectUtil.isNull(authentication)
                || ObjectUtil.isEmpty(authentication.getCredentials())) {
            throw UserExceptionEnum.USER_PASSWORD_NOT_MATCH.toException();
        }
        // 获取当前用户的输入认证对象
        String username = authentication.getName();
        // 获取当前用户的输入认证凭证
        String rawPassword = authentication.getCredentials().toString();
        // 从缓存中获取用户错误次数
        Integer retryCount = redisCache.getCacheObject(getCacheKey(role, username));

        if (ObjectUtil.isEmpty(retryCount)) {
            retryCount = 0;
        }
        if (retryCount >= maxRetryCount) {
            throw UserExceptionEnum.USER_PASSWORD_RETRY_LIMIT_EXCEED.toException(new Object[]{retryCount, lockTime});
        }
        String password = validUser.getPassword();
        if (!matches(password, rawPassword)) {
            retryCount += 1;
            redisCache.setCacheObject(getCacheKey(role, username), retryCount, lockTime, TimeUnit.MINUTES);
            throw UserExceptionEnum.USER_PASSWORD_NOT_MATCH.toException();
        }

        redisCache.deleteObject(getCacheKey(role, username));
    }


    /**
     * 密码匹配
     *
     * @param password    加密后的密码
     * @param rawPassword 明文密码
     * @return 是否匹配
     */
    public boolean matches(String password, String rawPassword) {
        return SecurityUtils.matchesPassword(password, rawPassword);
    }

}
