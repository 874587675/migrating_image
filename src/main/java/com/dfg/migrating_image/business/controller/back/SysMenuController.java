package com.dfg.migrating_image.business.controller.back;

import com.dfg.migrating_image.business.param.page.SysMenuPageParam;
import com.dfg.migrating_image.business.param.page.vo.PageVO;
import com.dfg.migrating_image.business.param.query.SysMenuQueryParam;
import com.dfg.migrating_image.business.param.remove.RemoveBaseParam;
import com.dfg.migrating_image.business.param.save.SysMenuSaveParam;
import com.dfg.migrating_image.business.param.update.SysMenuUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysMenuVO;
import com.dfg.migrating_image.business.service.SysMenuService;
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
 * 菜单权限 模块
 *
 * @author zgc
 * @since 2025-06-30 11:38:23
 */
@Api(value = "菜单权限模块", tags = "菜单权限模块相关接口")
@RestController
@RequestMapping("/api/v1/sysMenu")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation(value = "新增菜单权限", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/saveSysMenu", produces = "application/json", consumes = "application/json")
    public R<?> saveSysMenu(@Validated @RequestBody SysMenuSaveParam sysMenuSaveParam) {
        sysMenuService.saveSysMenu(sysMenuSaveParam);
        return R.ok();
    }

    @ApiOperation(value = "根据主键修改菜单权限", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/updateSysMenu", produces = "application/json", consumes = "application/json")
    public R<?> updateSysMenu(@Validated @RequestBody SysMenuUpdateParam sysMenuUpdateParam) {
        sysMenuService.updateSysMenu(sysMenuUpdateParam);
        return R.ok();
    }

    @ApiOperation(value = "根据主键删除菜单权限", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/removeSysMenu", produces = "application/json", consumes = "application/json")
    public R<?> removeSysMenu(@Validated @RequestBody RemoveBaseParam removeBaseParam) {
        sysMenuService.removeSysMenu(removeBaseParam);
        return R.ok();
    }

    @ApiOperation(value = "后台用户查询菜单权限列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backListSysMenu", produces = "application/json", consumes = "application/json")
    public R<List<SysMenuVO>> backListSysMenu() {
        return R.ok(sysMenuService.backListSysMenu());
    }

    @ApiOperation(value = "后台用户分页查询菜单权限列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backPageSysMenu", produces = "application/json", consumes = "application/json")
    public R<PageVO<SysMenuVO>> backPageSysMenu(@Validated @RequestBody SysMenuPageParam sysMenuPageParam) {
        return R.ok(sysMenuService.backPageSysMenu(sysMenuPageParam));
    }

    @ApiOperation(value = "后台用户查询菜单权限详情", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backQuerySysMenu", produces = "application/json", consumes = "application/json")
    public R<SysMenuVO> backQuerySysMenu(@Validated @RequestBody SysMenuQueryParam sysMenuQueryParam) {
        return R.ok(sysMenuService.backQuerySysMenu(sysMenuQueryParam));
    }

}
