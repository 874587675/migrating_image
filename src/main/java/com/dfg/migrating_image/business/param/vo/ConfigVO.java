package com.dfg.migrating_image.business.param.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.io.Serial;

/**
* 配置VO对象
* @author zgc
* @since 2026-04-04 15:06:19
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "配置VO对象", description = "配置VO对象")
public class ConfigVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="配置ID")
    private String configId;

    @ApiModelProperty(value="配置KEY")
    private String configKey;

    @ApiModelProperty(value="配置名称")
    private String configName;

    @ApiModelProperty(value="配置值")
    private String configValue;

    @ApiModelProperty(value="排序")
    private Integer sort;

    @ApiModelProperty(value="状态（0-禁用 1-启用）")
    private Integer status;

    @ApiModelProperty(value="配置类型（1=图片，2=富文本，3=文字）")
    private Integer configType;

}
