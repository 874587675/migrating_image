package com.dfg.migrating_image.business.param.page;

import java.io.Serializable;
import com.dfg.migrating_image.business.param.page.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serial;


/**
* 角色和菜单关联列表参数对象
* @author zgc
* @since 2025-06-30 11:42:56
*/
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ApiModel(value = "角色和菜单关联查询列表参数", description = "角色和菜单关联查询列表参数对象")
public class SysRoleMenuPageParam extends PageBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="角色菜单主键")
    private String roleMenuId;

}
