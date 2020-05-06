package com.qianlima.application.mapper.qianlima;


import com.qianlima.application.domain.qianlima.EnjinfoDetail;
import com.qianlima.application.domain.qianlima.EnjinfoGsInfo;

import java.util.List;

public interface EnjinfoMapper {
    List<EnjinfoDetail> selectEnjinfoDetailByIds(List<Integer> ids);

    List<EnjinfoGsInfo> selectEnjinfoGsInfoByIds(List<Integer> ids);
}