package com.qianlima.application.request.projectattention;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author ZHangYJ
 */
@Getter
@Setter
public class CancelAttentionParam {

    @NotEmpty
    @ApiModelProperty(value = "主键")
    List<Integer> idArr;
}
