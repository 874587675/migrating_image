package com.dfg.java_template.business.param.list;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import com.dfg.java_template.business.entity.Config;
import java.io.Serial;


/**
* 配置列表参数对象
* @author zgc
* @since 2026-04-04 15:06:19
*/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "配置查询列表参数", description = "配置查询列表参数对象")
public class ConfigListParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="配置ID")
    private String configId;

}
