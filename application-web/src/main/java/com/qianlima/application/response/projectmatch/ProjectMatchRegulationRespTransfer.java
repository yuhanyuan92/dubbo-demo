package com.qianlima.application.response.projectmatch;

import com.qianlima.application.dto.projectmatch.ProjectMatchRegulationDto;
import com.qianlima.application.dto.projectmatch.ProjectRecordDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMatchRegulationRespTransfer {

    ProjectMatchRegulationRespTransfer MAPPER = Mappers.getMapper(ProjectMatchRegulationRespTransfer.class);

    ProjectMatchRegulationResp fromProjectMatchRegulationDto(ProjectMatchRegulationDto projectMatchRegulationDto);

    ProjectInfoResp fromProjectRecordDto(ProjectRecordDto projectRecordDto);
}
