package com.qianlima.application.request.projectattention;

import com.qianlima.application.dto.projectattention.MakeAttentionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ZHangYJ
 */
@Mapper
public interface MakeAttentionTransfer {

    MakeAttentionTransfer MAPPER = Mappers.getMapper( MakeAttentionTransfer.class );

    @Mapping(source = "projectId", target = "projectId")
    MakeAttentionDto toMakeAttentionDto(MakeAttentionParam makeAttentionParam);
}
