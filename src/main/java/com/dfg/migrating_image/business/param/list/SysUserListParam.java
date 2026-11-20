package com.dfg.migrating_image.business.param.list;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;


/**
* 后台用户列表参数对象
* @author zgc
* @since 2025-11-02 19:31:23
*/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "后台用户查询列表参数", description = "后台用户查询列表参数对象")
public class SysUserListParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="后台用户ID")
    private String userId;

}
