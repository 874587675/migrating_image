package com.dfg.migrating_image.business.controller.back;

import com.dfg.migrating_image.business.param.list.ConfigListParam;
import com.dfg.migrating_image.business.param.page.ConfigPageParam;
import com.dfg.migrating_image.business.param.page.vo.PageVO;
import com.dfg.migrating_image.business.param.query.ConfigQueryParam;
import com.dfg.migrating_image.business.param.remove.RemoveBaseParam;
import com.dfg.migrating_image.business.param.save.ConfigSaveParam;
import com.dfg.migrating_image.business.param.update.ConfigUpdateParam;
import com.dfg.migrating_image.business.param.vo.ConfigVO;
import com.dfg.migrating_image.business.service.ConfigService;
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
 * 配置 模块
 *
 * @author zgc
 * @since 2026-04-04 15:06:19
 */
@Api(value = "配置模块", tags = "配置模块相关接口")
@RestController
@RequestMapping("/api/v1/sysConfig")
public class SysConfigController {
    @Resource
    private ConfigService configService;

    @PostMapping(value = "/back/saveConfig", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "新增配置", produces = "application/json", consumes = "application/json")
    public R<?> saveConfig(@Validated @RequestBody ConfigSaveParam configSaveParam) {
        configService.saveConfig(configSaveParam);
        return R.ok();
    }

    @PostMapping(value = "/back/updateConfig", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "根据主键修改配置", produces = "application/json", consumes = "application/json")
    public R<?> updateConfig(@Validated @RequestBody ConfigUpdateParam configUpdateParam) {
        configService.updateConfig(configUpdateParam);
        return R.ok();
    }
    
    @ApiOperation(value = "根据主键删除配置", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/removeConfig", produces = "application/json", consumes = "application/json")
    public R<?> removeConfig(@Validated @RequestBody RemoveBaseParam removeBaseParam) {
        configService.removeConfig(removeBaseParam);
        return R.ok();
    }

    @ApiOperation(value = "后台用户查询配置列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backListConfig", produces = "application/json", consumes = "application/json")
    public R<List<ConfigVO>> backListConfig(@Validated @RequestBody ConfigListParam configListParam) {
        return R.ok(configService.backListConfig(configListParam));
    }

    @ApiOperation(value = "后台用户分页查询配置列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backPageConfig", produces = "application/json", consumes = "application/json")
    public R<PageVO<ConfigVO>> backPageConfig(@Validated @RequestBody ConfigPageParam configPageParam) {
        return R.ok(configService.backPageConfig(configPageParam));
    }

    @ApiOperation(value = "后台用户查询配置详情", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/back/backQueryConfig", produces = "application/json", consumes = "application/json")
    public R<ConfigVO> backQueryConfig(@Validated @RequestBody ConfigQueryParam configQueryParam) {
        return R.ok(configService.backQueryConfig(configQueryParam));
    }

}
