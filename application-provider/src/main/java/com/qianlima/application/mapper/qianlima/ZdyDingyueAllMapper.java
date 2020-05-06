package com.qianlima.application.mapper.qianlima;

import com.qianlima.application.domain.qianlima.ZdyDingyueAll;

import java.util.List;

public interface ZdyDingyueAllMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZdyDingyueAll record);

    int insertSelective(ZdyDingyueAll record);

    ZdyDingyueAll selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZdyDingyueAll record);

    int updateByPrimaryKey(ZdyDingyueAll record);

    int selectCountByUserid(Long userid);

    List<ZdyDingyueAll> selectListById(Long userid);

    int selectCountByTitleAndUserid(Long userid,String title);

}