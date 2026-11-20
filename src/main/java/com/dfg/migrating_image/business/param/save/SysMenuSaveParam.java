package com.dfg.migrating_image.business.param.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.io.Serial;

/**
* 菜单新增参数对象
* @author zgc
* @since 2025-09-24 20:48:40
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "菜单新增参数", description = "菜单新增参数对象")
public class SysMenuSaveParam implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "父菜单ID")
    private String parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer menuSort;

    @ApiModelProperty(value = "菜单类型（1-目录 2-菜单 3-按钮）")
    private Integer menuType;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "路由名称")
    private String routeName;

    @ApiModelProperty(value = "菜单显示（0隐藏 1显示）")
    private Integer visible;

    @ApiModelProperty(value = "菜单状态（0停用 1正常）")
    private Integer status;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "外链地址（0否 1是）")
    private Integer isFrame;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    @ApiModelProperty(value = "备注")
    private String remark;

}
