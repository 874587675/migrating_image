package com.dfg.java_template.business.controller.front;

import com.dfg.java_template.business.param.list.ConfigListParam;
import com.dfg.java_template.business.param.page.ConfigPageParam;
import com.dfg.java_template.business.param.page.vo.PageVO;
import com.dfg.java_template.business.param.query.ConfigQueryParam;
import com.dfg.java_template.business.param.vo.ConfigVO;
import com.dfg.java_template.business.service.ConfigService;
import com.dfg.java_template.common.web.R;
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
@RequestMapping("/api/v1/config")
public class ConfigController {
    @Resource
    private ConfigService configService;

    @ApiOperation(value = "前台用户查询配置列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/front/frontListConfig", produces = "application/json", consumes = "application/json")
    public R<List<ConfigVO>> frontListConfig(@Validated @RequestBody ConfigListParam configListParam) {
        return R.ok(configService.frontListConfig(configListParam));
    }

    @ApiOperation(value = "前台用户分页查询配置列表", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/front/frontPageConfig", produces = "application/json", consumes = "application/json")
    public R<PageVO<ConfigVO>> frontPageConfig(@Validated @RequestBody ConfigPageParam configPageParam) {
        return R.ok(configService.frontPageConfig(configPageParam));
    }

    @ApiOperation(value = "前台用户查询配置详情", produces = "application/json", consumes = "application/json")
    @PostMapping(value = "/front/frontQueryConfig", produces = "application/json", consumes = "application/json")
    public R<ConfigVO> frontQueryConfig(@Validated @RequestBody ConfigQueryParam configQueryParam) {
        return R.ok(configService.frontQueryConfig(configQueryParam));
    }

}
