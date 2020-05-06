package com.qianlima.application.mapper.qlmservice;

import com.qianlima.application.domain.qlmservice.XSSelledInfo;
import org.apache.ibatis.annotations.Param;

public interface XSSelledInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XSSelledInfo record);

    int insertSelective(XSSelledInfo record);

    XSSelledInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(XSSelledInfo record);

    int updateByPrimaryKey(XSSelledInfo record);

    XSSelledInfo selectByCid(@Param("userId") Integer userId);
}