package com.dfg.java_template.framework.security.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "微信无感登录参数", description = "微信无感登录参数对象")
public class WxLoginByCodeBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权code", required = true)
    @NotBlank
    private String code;

    @ApiModelProperty("邀请码")
    private String invitedCode;
}
