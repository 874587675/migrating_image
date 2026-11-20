package com.dfg.java_template.business.param.page.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * 分页查询请求基类BO
 *
 * @author zgc
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ApiModel(value = "分页查询请求基类BO", description = "分页查询请求基类BO对象")
public class PageBO {

    @ApiModelProperty(value = "页码", required = true)
    @NotNull(message = "页码不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页数据条数", required = true)
    @NotNull(message = "每页数据条数不能为空")
    private Integer pageSize;

}
