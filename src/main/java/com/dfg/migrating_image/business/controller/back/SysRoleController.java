package com.dfg.migrating_image.business.controller.back;

import com.dfg.migrating_image.business.param.list.SysRoleListParam;
import com.dfg.migrating_image.business.param.page.SysRolePageParam;
import com.dfg.migrating_image.business.param.page.vo.PageVO;
import com.dfg.migrating_image.business.param.query.SysRoleQueryParam;
import com.dfg.migrating_image.business.param.remove.RemoveBaseParam;
import com.dfg.migrating_image.business.param.save.SysRoleSaveParam;
import com.dfg.migrating_image.business.param.update.SysRoleUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysRoleVO;
import com.dfg.migrating_image.business.service.SysRoleService;
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
* 角色信息 模块
* @author zgc
* @since 2025-06-30 11:42:56
*/
@Api(value = "角色信息模块", tags = "角色信息模块相关接口")
@RestController
@RequestMapping("/api/v1/sysRole")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation(value = "新增角色信息", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/saveSysRole", produces = "application/json", consumes = "application/json")
    public R<?> saveSysRole(@Validated @RequestBody SysRoleSaveParam sysRoleSaveParam){
        sysRoleService.saveSysRole(sysRoleSaveParam);
        return R.ok();
    }

    @ApiOperation(value = "根据主键修改角色信息", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/updateSysRole", produces = "application/json", consumes = "application/json")
    public R<?> updateSysRole(@Validated @RequestBody SysRoleUpdateParam sysRoleUpdateParam){
        sysRoleService.updateSysRole(sysRoleUpdateParam);
        return R.ok();
    }

    @ApiOperation(value = "根据主键删除角色信息", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/removeSysRole", produces = "application/json", consumes = "application/json")
    public R<?> removeSysRole(@Validated @RequestBody RemoveBaseParam removeBaseParam){
        sysRoleService.removeSysRole(removeBaseParam);
        return R.ok();
    }

    @ApiOperation(value = "后台用户查询角色信息列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backListSysRole", produces = "application/json", consumes = "application/json")
    public R<List<SysRoleVO>> backListSysRole(@Validated @RequestBody SysRoleListParam sysRoleListParam){
        return R.ok(sysRoleService.backListSysRole(sysRoleListParam));
    }

    @ApiOperation(value = "后台用户分页查询角色信息列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backPageSysRole", produces = "application/json", consumes = "application/json")
    public R<PageVO<SysRoleVO>> backPageSysRole(@Validated @RequestBody SysRolePageParam sysRolePageParam){
        return R.ok(sysRoleService.backPageSysRole(sysRolePageParam));
    }
    
    @ApiOperation(value = "后台用户查询角色信息详情", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backQuerySysRole", produces = "application/json", consumes = "application/json")
    public R<SysRoleVO> backQuerySysRole(@Validated @RequestBody SysRoleQueryParam sysRoleQueryParam){
        return R.ok(sysRoleService.backQuerySysRole(sysRoleQueryParam));
    }


}
