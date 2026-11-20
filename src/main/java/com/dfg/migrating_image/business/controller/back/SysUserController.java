package com.dfg.migrating_image.business.controller.back;

import com.dfg.migrating_image.business.param.list.SysUserListParam;
import com.dfg.migrating_image.business.param.page.SysUserPageParam;
import com.dfg.migrating_image.business.param.page.UserPageParam;
import com.dfg.migrating_image.business.param.page.vo.PageVO;
import com.dfg.migrating_image.business.param.query.SysUserQueryParam;
import com.dfg.migrating_image.business.param.query.UserQueryParam;
import com.dfg.migrating_image.business.param.remove.RemoveBaseParam;
import com.dfg.migrating_image.business.param.remove.RemoveBatchParam;
import com.dfg.migrating_image.business.param.save.SysUserSaveParam;
import com.dfg.migrating_image.business.param.update.SysUserUpdateParam;
import com.dfg.migrating_image.business.param.update.UserUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysUserVO;
import com.dfg.migrating_image.business.param.vo.UserVO;
import com.dfg.migrating_image.business.service.SysUserService;
import com.dfg.migrating_image.business.service.UserService;
import com.dfg.migrating_image.common.web.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 后台用户 模块
 *
 * @author zgc
 * @since 2025-06-30 11:42:56
 */
@Api(value = "后台用户模块", tags = "后台用户模块相关接口")
@RestController
@RequestMapping("/api/v1/sysUser")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private UserService userService;

    @ApiOperation(value = "新增后台用户", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/saveSysUser", produces = "application/json", consumes = "application/json")
    public R<?> saveSysUser(@Validated @RequestBody SysUserSaveParam sysUserSaveParam) {
        sysUserService.saveSysUser(sysUserSaveParam);
        return R.ok();
    }

    @ApiOperation(value = "根据主键修改后台用户", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/updateSysUser", produces = "application/json", consumes = "application/json")
    public R<?> updateSysUser(@Validated @RequestBody SysUserUpdateParam sysUserUpdateParam) {
        sysUserService.updateSysUser(sysUserUpdateParam);
        return R.ok();
    }

    @ApiOperation(value = "根据主键删除后台用户", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/removeSysUser", produces = "application/json", consumes = "application/json")
    public R<?> removeSysUser(@Validated @RequestBody RemoveBaseParam removeBaseParam) {
        sysUserService.removeSysUser(removeBaseParam);
        return R.ok();
    }

    @ApiOperation(value = "根据主键批量删除后台用户", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/removeBatchSysUser", produces = "application/json", consumes = "application/json")
    public R<?> removeBatchSysUser(@Validated @RequestBody RemoveBatchParam removeBatchParam) {
        sysUserService.removeBatchSysUser(removeBatchParam);
        return R.ok();
    }

    @ApiOperation(value = "后台用户查询后台用户列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backListSysUser", produces = "application/json", consumes = "application/json")
    public R<List<SysUserVO>> backListSysUser(@Validated @RequestBody SysUserListParam sysUserListParam) {
        return R.ok(sysUserService.backListSysUser(sysUserListParam));
    }

    @ApiOperation(value = "后台用户分页查询后台用户列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backPageSysUser", produces = "application/json", consumes = "application/json")
    public R<PageVO<SysUserVO>> backPageSysUser(@Validated @RequestBody SysUserPageParam sysUserPageParam) {
        return R.ok(sysUserService.backPageSysUser(sysUserPageParam));
    }

    @ApiOperation(value = "后台用户查询后台用户详情", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backQuerySysUser", produces = "application/json", consumes = "application/json")
    public R<SysUserVO> backQuerySysUser(@Validated @RequestBody SysUserQueryParam sysUserQueryParam) {
        return R.ok(sysUserService.backQuerySysUser(sysUserQueryParam));
    }

    @PostMapping(value = "/back/backUpdateUser", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "根据主键修改前台用户信息", produces = "application/json", consumes = "application/json")
    public R<?> backUpdateUser(@Validated @RequestBody UserUpdateParam userUpdateParam) {
        userService.updateUser(userUpdateParam);
        return R.ok();
    }

    @ApiOperation(value = "查询前台用户信息详情", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backQueryUser", produces = "application/json", consumes = "application/json")
    public R<UserVO> backQueryUser(@Validated @RequestBody UserQueryParam userQueryParam) {
        return R.ok(userService.backQueryUser(userQueryParam));
    }

    @ApiOperation(value = "后台用户分页查询前台用户列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backPageUser", produces = "application/json", consumes = "application/json")
    public R<PageVO<UserVO>> backPageUser(@Validated @RequestBody UserPageParam userPageParam) {
        return R.ok(userService.backPageUser(userPageParam));
    }
}
