package com.qianlima.application.mapper.qianlima;

import com.github.pagehelper.Page;
import com.qianlima.application.domain.qianlima.ZdyXmZd;
import com.qianlima.application.domain.qianlima.ZdyXmZdWithBLOBs;
import feign.Param;

import java.util.List;

public interface ZdyXmZdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZdyXmZdWithBLOBs record);

    int insertSelective(ZdyXmZdWithBLOBs record);

    ZdyXmZdWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZdyXmZdWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ZdyXmZdWithBLOBs record);

    int updateByPrimaryKey(ZdyXmZd record);

    int countByUserId(Integer userId);

    ZdyXmZdWithBLOBs selectByUserIdAndContentId(Integer uid, Integer contentid);

    Page<ZdyXmZd> selectByUserId(@Param("userId") Integer userId, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
}