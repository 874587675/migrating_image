package com.dfg.java_template.framework.security.service.login;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dfg.java_template.business.entity.User;
import com.dfg.java_template.business.mapper.UserMapper;
import com.dfg.java_template.business.param.convertor.UserConvertor;
import com.dfg.java_template.business.param.vo.UserVO;
import com.dfg.java_template.common.constant.CacheConstants;
import com.dfg.java_template.common.exception.ServiceExceptionEnum;
import com.dfg.java_template.common.exception.user.UserExceptionEnum;
import com.dfg.java_template.framework.redis.RedisCache;
import com.dfg.java_template.framework.security.constant.LoginRoleEnum;
import com.dfg.java_template.framework.security.param.LoginUser;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FrontendUserDetailsService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordService passwordService;
    @Resource
    private RedisCache redisCache;
    @Resource
    private WxMaService wxMaService;


    /**
     * 根据用户名加载用户信息
     *
     * @param username 用户名
     * @return UserDetails 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        //根据用户名查询用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username).last("limit 1"));
        if (ObjectUtil.isEmpty(user)) {
            throw UserExceptionEnum.USER_PASSWORD_NOT_MATCH.toException();
        }
        UserVO userVO = UserConvertor.QUERY.entityToVo(user);
        //验证密码
        passwordService.validatePassword(userVO, LoginRoleEnum.FRONT_ROLE.getCode());
        //返回用户信息
        return createLoginUser(userVO);
    }

    /**
     * 创建登录用户
     *
     * @param userVO 用户信息
     * @return UserDetails 登录用户信息
     */
    public UserDetails createLoginUser(UserVO userVO) {
        Set<String> roles = new HashSet<>();
        List<String> permissions = new ArrayList<>();
        return new LoginUser(userVO.getUserId(), userVO.getUserName(), userVO.getPassword(), userVO.getStatus(), roles, permissions,userVO.getLastIpAddress());
    }

    /**
     * 小程序获取用户手机号
     *
     * @param appid      小程序appid
     * @param phoneCode  手机号code
     * @return WxMaPhoneNumberInfo 手机号信息
     */
    public WxMaPhoneNumberInfo getPhoneNumber(String appid, String phoneCode) {
        String wxAccessToken = redisCache.getCacheObject(CacheConstants.WX_ACCESS_TOKEN + appid);
        if (ObjectUtil.isEmpty(wxAccessToken)) {
            throw ServiceExceptionEnum.MINI_CONFIG_ERROR.toException();
        }
        try {
            WxMaPhoneNumberInfo phoneInfo = wxMaService.getUserService().getPhoneNumber(phoneCode);
            if (ObjectUtil.isEmpty(phoneInfo)) {
                throw ServiceExceptionEnum.GET_PHONE_ERROR.toException();
            }
            return phoneInfo;
        } catch (WxErrorException e) {
            throw ServiceExceptionEnum.GET_PHONE_ERROR.toException();
        }
    }

    public WxMaJscode2SessionResult getSessionInfo(String code) {
        try{
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            if (ObjectUtil.isEmpty(sessionInfo)) {
                throw ServiceExceptionEnum.GET_USER_INFO_ERROR.toException();
            }
            return sessionInfo;
        }catch (WxErrorException e){
            throw ServiceExceptionEnum.GET_USER_INFO_ERROR.toException();
        }
    }



}
