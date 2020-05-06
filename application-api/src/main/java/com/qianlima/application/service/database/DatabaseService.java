package com.qianlima.application.service.database;


import com.qianlima.application.dto.projectattention.DatabaseParamDto;

import java.util.Map;

/**
 * 企业库 、 设计库
 */
public interface DatabaseService {

    /**
     * 供应商权限
     */
    boolean getSupplierPermission(Long userid);

    /**
     * 数据库权限 - 企业库  type = 0、 设计库  type = 1
     */
    boolean getPermission(String username, int type);

    /**
     * 数据库权限 - 一天40页
     */
    boolean getBrowsePermission(String username);

    /**
     * 企业库数据
     */
    Map<String,Object> getQyDatabase(DatabaseParamDto databaseParamDto);

    /**
     * 设计库数据
     */
    Map<String,Object> getSjDatabase(DatabaseParamDto databaseParamDto);
}
