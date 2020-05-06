package com.qianlima.application.response.companymonitor;

import com.qianlima.application.dto.companymonitor.CompanyMonitorNameDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMonitorNameRespTransfer {
    CompanyMonitorNameRespTransfer MAPPER = Mappers.getMapper(CompanyMonitorNameRespTransfer.class);

    CompanyMonitorNameResp fromCompanyMonitorNameDto(CompanyMonitorNameDto companyMonitorNameDto);
}
