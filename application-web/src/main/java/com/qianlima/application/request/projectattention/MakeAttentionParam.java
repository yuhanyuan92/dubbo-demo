package com.qianlima.application.request.projectattention;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author ZHangYJ
 */
@ApiModel(value = "项目专耵请求")
@Data
public class MakeAttentionParam {

    @NotNull(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @NotEmpty(message = "项目标题不能为空")
    @ApiModelProperty(value = "项目标题")
    private String projectTitle;

    @NotEmpty(message = "专耵内容不能为空")
    @ApiModelProperty(value = "专耵内容")
    private String text;
}
