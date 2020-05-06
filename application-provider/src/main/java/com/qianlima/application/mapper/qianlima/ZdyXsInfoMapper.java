package com.qianlima.application.mapper.qianlima;

import com.qianlima.application.domain.qianlima.ZdyXsInfo;
import org.apache.ibatis.annotations.Param;

public interface ZdyXsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZdyXsInfo record);

    int insertSelective(ZdyXsInfo record);

    ZdyXsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZdyXsInfo record);

    int updateByPrimaryKey(ZdyXsInfo record);

    ZdyXsInfo getCustomerServiceInfo(@Param("userId") Integer userId);
}