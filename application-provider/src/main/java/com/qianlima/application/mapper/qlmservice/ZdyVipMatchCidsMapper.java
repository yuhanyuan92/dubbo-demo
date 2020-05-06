package com.qianlima.application.mapper.qlmservice;

import com.github.pagehelper.Page;
import com.qianlima.application.domain.qlmservice.ZdyVipMatchCids;
import feign.Param;

public interface ZdyVipMatchCidsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZdyVipMatchCids record);

    int insertSelective(ZdyVipMatchCids record);

    ZdyVipMatchCids selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZdyVipMatchCids record);

    int updateByPrimaryKey(ZdyVipMatchCids record);

    Page<ZdyVipMatchCids> selectByMatchId(@Param("matchId") int matchId, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
}