package com.dfg.java_template.business.param.remove;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量删除参数
 * @author zgc
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(value = "批量删除参数", description = "批量删除参数对象")
public class RemoveBatchParam {

    @ApiModelProperty(value = "主键ID列表", required = true)
    @NotEmpty(message = "主键ID列表不能为空")
    private List<String> serializableIds;
}
