package com.qianlima.application.response.projectattention;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目专耵
 * @author ZHangYJ
 */
@Data
@ApiModel(value = "项目专耵")
public class ProjectAttentionResp {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "专耵状态<br>" +
            " 0 = 该项目已被您专盯过，您可通过会员中心查看已申请的项目专盯。" +
            " 1 = 此数据已经被您专盯处理过,不可再次专盯" +
            " 2 = 该项目已被您取消专盯，请联系您的专属客服。" +
            " 3 = 该项目已被您专盯过，您可通过会员中心查看已申请的项目专盯。")
    private Integer status;

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectTitle;

    @ApiModelProperty(value = "catId")
    private String catId;

    @ApiModelProperty(value = "地区")
    private String areaName;

    @ApiModelProperty(value = "进展阶段")
    private String progress;

    @ApiModelProperty(value = "业主类型")
    private String ownerType;

    @ApiModelProperty(value = "项目类别")
    private String prog;

    @ApiModelProperty(value = "项目投资")
    private String investment;

    @ApiModelProperty(value = "装修情况")
    private String decoration;

    @ApiModelProperty(value = "跟进")
    private Integer trace;

    @ApiModelProperty(value = "更新时间/秒")
    private Long updateTime;

    @ApiModelProperty(value = "申请时间/秒")
    private Long addTime;

}
