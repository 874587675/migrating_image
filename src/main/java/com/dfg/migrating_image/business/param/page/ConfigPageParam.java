package com.dfg.migrating_image.business.param.page;

import java.io.Serializable;
import com.dfg.migrating_image.business.param.page.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serial;


/**
* 配置列表参数对象
* @author zgc
* @since 2026-04-04 15:06:19
*/
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ApiModel(value = "配置查询列表参数", description = "配置查询列表参数对象")
public class ConfigPageParam extends PageBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="配置ID")
    private String configId;

}
