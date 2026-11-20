package com.dfg.java_template.business.controller.front;

import com.dfg.java_template.business.service.UserService;
import com.dfg.java_template.common.web.R;
import com.dfg.java_template.framework.security.param.LoginBody;
import com.dfg.java_template.framework.security.param.LoginVO;
import com.dfg.java_template.framework.security.param.WxLoginByCodeBody;
import com.dfg.java_template.framework.security.param.WxLoginByPhoneBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(value = "登录模块", tags = "登录模块相关接口")
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    @Resource
    private UserService userService;

    @Deprecated
    @ApiOperation(value = "前台用户账号密码登录", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/loginUser", produces = "application/json", consumes = "application/json")
    public R<LoginVO> loginUser(@Validated @RequestBody LoginBody loginBody, HttpServletRequest httpServletRequest) {
        return R.ok(userService.loginUser(loginBody, httpServletRequest));
    }

    @ApiOperation(value = "前台用户退出登录", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/front/exitUser", produces = "application/json", consumes = "application/json")
    public R<String> exitUser() {
        return R.ok(userService.exitUser());
    }

    @ApiOperation(value = "前台用户微信手机号快捷登录", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/wxLoginByPhone", produces = "application/json", consumes = "application/json")
    public R<LoginVO> wxLoginByPhone(@Validated @RequestBody WxLoginByPhoneBody wxLoginBody, HttpServletRequest httpServletRequest) {
        return R.ok(userService.wxMiniLoginByPhone(wxLoginBody, httpServletRequest));
    }

    @ApiOperation(value = "前台用户微信一键快捷登录", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/wxLoginByQuick", produces = "application/json", consumes = "application/json")
    public R<LoginVO> wxLoginByQuick(@Validated @RequestBody WxLoginByCodeBody wxLoginBody, HttpServletRequest httpServletRequest) {
        return R.ok(userService.wxLoginByQuick(wxLoginBody, httpServletRequest));
    }

    @ApiOperation(value = "刷新令牌", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/refresh", produces = "application/json", consumes = "application/json")
    public R<String> refresh(HttpServletRequest httpServletRequest) {
        return R.ok(userService.refresh(httpServletRequest));
    }
}
