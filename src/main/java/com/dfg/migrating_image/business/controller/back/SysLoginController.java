package com.dfg.migrating_image.business.controller.back;

import com.dfg.migrating_image.business.service.SysUserService;
import com.dfg.migrating_image.common.web.R;
import com.dfg.migrating_image.framework.security.param.SysLoginBody;
import com.dfg.migrating_image.framework.security.param.SysLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "登录模块", tags = "登录模块相关接口")
@RestController
@RequestMapping("/api/v1/sysLogin")
public class SysLoginController {
    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value = "后台用户账号密码登录", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/loginSysUser", produces = "application/json", consumes = "application/json")
    public R<SysLoginVO> loginSysUser(@Validated @RequestBody SysLoginBody sysLoginBody, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return R.ok(sysUserService.loginSysUser(sysLoginBody, httpServletRequest, httpServletResponse));
    }

    @ApiOperation(value = "后台用户退出", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/exitSysUser", produces = "application/json", consumes = "application/json")
    public R<?> exitSysUser(HttpServletResponse httpServletResponse) {
        sysUserService.exitSysUser(httpServletResponse);
        return R.ok();
    }

    @ApiOperation(value = "刷新令牌", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/refresh", produces = "application/json", consumes = "application/json")
    public R<SysLoginVO> refresh(HttpServletRequest httpServletRequest) {
        return R.ok(sysUserService.refresh(httpServletRequest));
    }
}
