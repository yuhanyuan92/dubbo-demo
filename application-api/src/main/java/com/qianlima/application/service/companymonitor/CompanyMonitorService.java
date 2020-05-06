package com.qianlima.application.service.companymonitor;

import com.qianlima.application.dto.companymonitor.CompanyMonitorNameDto;
import com.qianlima.application.dto.companymonitor.ZdyDingyueAllDto;

import java.util.List;

public interface CompanyMonitorService {
    List<CompanyMonitorNameDto> getCompanyNameList(Long id);

    Boolean companyMonitorPermission(Long userid);

    Integer addCompanyMonitor(String titles, Long userid);

    Integer modifyCompanyInfo(ZdyDingyueAllDto zdyDingyueAll);

    Integer delCompanyInfo(Integer id);

    CompanyMonitorNameDto getCompanyInfo(Integer id);
}
