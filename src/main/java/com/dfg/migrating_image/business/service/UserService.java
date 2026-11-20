package com.dfg.migrating_image.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfg.migrating_image.business.entity.User;
import com.dfg.migrating_image.business.param.page.UserPageParam;
import com.dfg.migrating_image.business.param.page.vo.PageVO;
import com.dfg.migrating_image.business.param.query.UserQueryParam;
import com.dfg.migrating_image.business.param.remove.RemoveBaseParam;
import com.dfg.migrating_image.business.param.save.UserSaveParam;
import com.dfg.migrating_image.business.param.update.UserUpdateParam;
import com.dfg.migrating_image.business.param.vo.UserVO;
import com.dfg.migrating_image.framework.security.param.LoginBody;
import com.dfg.migrating_image.framework.security.param.LoginVO;
import com.dfg.migrating_image.framework.security.param.WxLoginByCodeBody;
import com.dfg.migrating_image.framework.security.param.WxLoginByPhoneBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zgc
 * @since 2025-07-01 15:18:53
 */
public interface UserService extends IService<User> {

    /**
     * 保存用户信息
     *
     * @param userSaveParam 保存用户信息参数
     */
    void saveUser(UserSaveParam userSaveParam);

    /**
     * 修改用户信息
     *
     * @param userUpdateParam 修改用户信息参数
     */
    void updateUser(UserUpdateParam userUpdateParam);

    /**
     * 删除用户信息
     *
     * @param removeBaseParam 删除用户信息参数
     */
    void removeUser(RemoveBaseParam removeBaseParam);

    /**
     * 前台用户查询用户信息详情
     *
     * @return 用户信息VO
     */
    UserVO frontQueryUser();

    /**
     * 用户登录
     *
     * @param loginBody 登录参数
     * @return LoginVO
     */
    LoginVO loginUser(LoginBody loginBody, HttpServletRequest httpServletRequest);

    /**
     * 用户退出登录
     *
     * @return 结果
     */
    String exitUser();

    /**
     * 微信手机号快捷登录
     *
     * @param wxLoginBody 微信登录参数
     * @return LoginVO
     */
    LoginVO wxMiniLoginByPhone(WxLoginByPhoneBody wxLoginBody, HttpServletRequest httpServletRequest);

    /**
     * 刷新令牌
     *
     * @param httpServletRequest 请求
     */
    String refresh(HttpServletRequest httpServletRequest);

    /**
     * 微信一键快捷登录
     *
     * @param wxLoginBody 微信登录参数
     * @return LoginVO
     */
    LoginVO wxLoginByQuick(WxLoginByCodeBody wxLoginBody, HttpServletRequest httpServletRequest);

    /**
     * 后台用户查询前台用户信息详情
     *
     * @param userQueryParam 用户查询参数
     * @return 用户信息VO
     */
    UserVO backQueryUser(UserQueryParam userQueryParam);

    /**
     * 后台用户分页查询前台用户列表
     *
     * @param userPageParam 用户查询参数
     * @return 用户信息VO
     */
    PageVO<UserVO> backPageUser(UserPageParam userPageParam);
}
