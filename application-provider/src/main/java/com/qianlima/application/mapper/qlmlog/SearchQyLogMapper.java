package com.qianlima.application.mapper.qlmlog;

public interface SearchQyLogMapper {
    Integer selectCountByUsername(String username,Long time);

    Integer selectCountByUrl(String username,String url,Long time);

    Integer insertJL(String username,String url,String ip,Long time);
}