package com.qianlima.application.manage.transfer;

import com.qianlima.application.domain.qlmservice.ZdyVipMatchCids;
import com.qianlima.application.domain.qlmservice.ZdyVipMatchFeedback;
import com.qianlima.application.domain.qlmservice.ZdyVipMatchWithBLOBs;
import com.qianlima.application.dto.projectmatch.ProjectMatchFeedbackDto;
import com.qianlima.application.dto.projectmatch.ProjectMatchRegulationDto;
import com.qianlima.application.dto.projectmatch.ProjectRecordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author hanyuan.yu
 */
@Mapper
public interface ProjectMatchDtoTransfer {

    ProjectMatchDtoTransfer MAPPER = Mappers.getMapper(ProjectMatchDtoTransfer.class);

    @Mapping(source = "yewu", target = "businessName")
    @Mapping(source = "areas", target = "areaIds")
    @Mapping(source = "areanames", target = "areaNames")
    ProjectMatchRegulationDto fromZdyVipMatchWithBLOBs(ZdyVipMatchWithBLOBs zdyVipMatchWithBLOBs);

    @Mapping(source = "contentId", target = "cid")
    ZdyVipMatchFeedback fromProjectMatchFeedbackDto(ProjectMatchFeedbackDto projectMatchFeedbackDto);

    @Mapping(source = "cid", target = "contentId")
    ProjectRecordDto fromZdyVipMatchCids(ZdyVipMatchCids zdyVipMatchCids);
}
