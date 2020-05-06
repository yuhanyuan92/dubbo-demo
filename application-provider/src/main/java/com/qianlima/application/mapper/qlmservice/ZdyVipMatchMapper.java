package com.qianlima.application.mapper.qlmservice;

import com.qianlima.application.domain.qlmservice.ZdyVipMatch;
import com.qianlima.application.domain.qlmservice.ZdyVipMatchWithBLOBs;

public interface ZdyVipMatchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZdyVipMatchWithBLOBs record);

    int insertSelective(ZdyVipMatchWithBLOBs record);

    ZdyVipMatchWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZdyVipMatchWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ZdyVipMatchWithBLOBs record);

    int updateByPrimaryKey(ZdyVipMatch record);

    ZdyVipMatchWithBLOBs selectByUserId(Long userId);

    long countByUserId(Integer userId);

    void updateByUserIdSelective(ZdyVipMatchWithBLOBs zdyVipMatchWithBLOBs);
}