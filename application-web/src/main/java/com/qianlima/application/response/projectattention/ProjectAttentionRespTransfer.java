package com.qianlima.application.response.projectattention;

import com.qianlima.application.dto.projectattention.MakeAttentionDto;
import com.qianlima.application.request.projectattention.MakeAttentionTransfer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 项目专耵
 * @author ZHangYJ
 */
@Mapper
public interface ProjectAttentionRespTransfer {

    ProjectAttentionRespTransfer MAPPER = Mappers.getMapper( ProjectAttentionRespTransfer.class );

    ProjectAttentionResp fromMakeAttention(MakeAttentionDto makeAttentionDto);
}
