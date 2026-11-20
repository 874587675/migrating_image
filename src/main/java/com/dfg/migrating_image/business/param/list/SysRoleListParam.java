package com.dfg.migrating_image.business.param.list;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;


/**
* 角色信息列表参数对象
* @author zgc
* @since 2025-06-30 11:42:56
*/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "角色信息查询列表参数", description = "角色信息查询列表参数对象")
public class SysRoleListParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="角色ID")
    private String roleId;

}
