package com.dfg.migrating_image.business.param.query;

import io.swagger.annotations.ApiModel;
import lombok.*;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.io.Serial;
/**
* 后台用户查询参数
* @author zgc
* @since 2025-11-02 19:31:23
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "后台用户查询参数", description = "后台用户查询参数对象")
public class SysUserQueryParam  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="后台用户ID")
    private String userId;
}
