package com.dfg.java_template.business.param.query;

import io.swagger.annotations.ApiModel;
import lombok.*;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.io.Serial;
/**
* 配置查询参数
* @author zgc
* @since 2026-04-04 15:06:19
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "配置查询参数", description = "配置查询参数对象")
public class ConfigQueryParam  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="配置ID",required = true)
    @NotBlank
    private String configId;
}
