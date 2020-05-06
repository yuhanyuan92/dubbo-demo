package com.qianlima.application.mapper.institution;


import com.qianlima.application.domain.institution.XmCompanyTest;

import java.util.List;

public interface XmCompanyTestMapper {
    List<XmCompanyTest> selectDataByIds(List<Integer> ids);
}