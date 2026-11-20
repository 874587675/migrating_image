package com.dfg.migrating_image.framework.security.param;

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
@ApiModel(value = "微信手机号登录参数", description = "微信手机号登录参数对象")
public class WxLoginByPhoneBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权code", required = true)
    @NotBlank
    private String code;

    @ApiModelProperty(value = "获取手机code", required = true)
    private String phoneCode;

    @ApiModelProperty("邀请码")
    private String invitedCode;
}
