package com.dfg.migrating_image.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import java.io.Serial;

/**
* 配置表实体类对象
*
* @author zgc
* @since 2026-04-04 15:06:19
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@TableName(value = "t_config")
@ApiModel(value = "Config对象", description = "配置表")
public class Config implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "配置ID")
    @TableId(value = "config_id", type = IdType.ASSIGN_ID)
    private String configId;

    @ApiModelProperty(value = "配置KEY")
    @TableField(value = "config_key")
    private String configKey;

    @ApiModelProperty(value = "配置名称")
    @TableField(value = "config_name")
    private String configName;

    @ApiModelProperty(value = "配置值")
    @TableField(value = "config_value")
    private String configValue;

    @ApiModelProperty(value = "排序")
    @TableField(value = "sort")
    private Integer sort;

    @ApiModelProperty(value = "状态（0-禁用 1-启用）")
    @TableField(value = "status")
    private Integer status;

    @ApiModelProperty(value = "配置类型（1=图片，2=富文本，3=文字）")
    @TableField(value = "config_type")
    private Integer configType;

    @ApiModelProperty(value = "创建者ID")
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private String createUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "更新者ID")
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除（0-未删除 1-已删除）")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "删除时间")
    @TableField(value = "delete_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deleteTime;

}