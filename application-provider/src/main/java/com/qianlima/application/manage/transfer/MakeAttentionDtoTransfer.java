package com.qianlima.application.manage.transfer;

import com.qianlima.application.domain.qianlima.ZdyXmZd;
import com.qianlima.application.domain.qianlima.ZdyXmZdWithBLOBs;
import com.qianlima.application.dto.projectattention.MakeAttentionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ZHangYJ
 */
@Mapper
public interface MakeAttentionDtoTransfer {

    MakeAttentionDtoTransfer MAPPER = Mappers.getMapper( MakeAttentionDtoTransfer.class );

    @Mapping(source = "userId", target = "uid")
    @Mapping(source = "projectId", target = "contentid")
    @Mapping(source = "projectTitle", target = "title")
    @Mapping(source = "text", target = "mText")
    ZdyXmZdWithBLOBs toZdyXmZdWithBLOBs(MakeAttentionDto makeAttentionDto);

    @Mapping(source = "uid", target = "userId")
    @Mapping(source = "contentid", target = "projectId")
    @Mapping(source = "title", target = "projectTitle")
    @Mapping(source = "mText", target = "text")
    MakeAttentionDto fromZdyXmZdWithBLOBs(ZdyXmZdWithBLOBs zdyXmZdWithBLOBs);

    @Mapping(source = "uid", target = "userId")
    @Mapping(source = "contentid", target = "projectId")
    @Mapping(source = "title", target = "projectTitle")
    @Mapping(source = "updatetime", target = "updateTime")
    @Mapping(source = "addtime", target = "addTime")
    MakeAttentionDto  fromZdyXmZd(ZdyXmZd zdyXmZd);
}
