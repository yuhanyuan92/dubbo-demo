package com.qianlima.application.mapper.qianlima;


import com.qianlima.application.domain.qianlima.ZdyMembersXwgz;

public interface ZdyMembersXwgzMapper {
    Integer insertUser(ZdyMembersXwgz zdyMembersXwgz);

    Integer selectFromByUserid(Long userid);

    Integer selectUnittypeByUsername(String username);
}