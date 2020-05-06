package com.qianlima.application.request.projectmatch;

import com.qianlima.application.dto.projectmatch.ProjectMatchFeedbackDto;
import com.qianlima.application.dto.projectmatch.ProjectMatchRegulationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMatchTransfer {

    ProjectMatchTransfer MAPPER = Mappers.getMapper(ProjectMatchTransfer.class);

    ProjectMatchRegulationDto fromProjectMatchRegulationParam(ProjectMatchRegulationParam projectMatchRegulationParam);

    ProjectMatchFeedbackDto fromProjectMatchFeedbackParam(ProjectMatchFeedbackParam projectMatchFeedbackParam);
}
