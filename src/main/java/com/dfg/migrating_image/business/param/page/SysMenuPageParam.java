package com.dfg.migrating_image.business.param.page;

import java.io.Serializable;
import com.dfg.migrating_image.business.param.page.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serial;


/**
* 菜单列表参数对象
* @author zgc
* @since 2025-09-24 20:48:40
*/
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ApiModel(value = "菜单查询列表参数", description = "菜单查询列表参数对象")
public class SysMenuPageParam extends PageBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="菜单ID")
    private String menuId;

}
