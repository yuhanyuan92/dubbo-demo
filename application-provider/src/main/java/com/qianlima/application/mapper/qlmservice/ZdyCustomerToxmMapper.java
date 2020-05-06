package com.qianlima.application.mapper.qlmservice;

import com.qianlima.application.domain.qlmservice.ZdyCustomerToxm;
import org.apache.ibatis.annotations.Param;

public interface ZdyCustomerToxmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZdyCustomerToxm record);

    int insertSelective(ZdyCustomerToxm record);

    ZdyCustomerToxm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZdyCustomerToxm record);

    int updateByPrimaryKey(ZdyCustomerToxm record);

    ZdyCustomerToxm selectByUserId(@Param("userId") Integer userId);
}