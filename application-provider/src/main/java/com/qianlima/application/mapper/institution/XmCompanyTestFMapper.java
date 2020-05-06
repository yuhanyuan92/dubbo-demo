package com.qianlima.application.mapper.institution;


import com.qianlima.application.domain.institution.XmCompanyTestF;

import java.util.List;

public interface XmCompanyTestFMapper {
    List<XmCompanyTestF> selectDataByIds(List<Integer> ids);
}