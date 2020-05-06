package com.qianlima.application.mapper.qianlima;

import com.qianlima.application.domain.qianlima.ZdySjkShow;
import org.apache.ibatis.annotations.Param;

public interface ZdySjkShowMapper {
    ZdySjkShow selectZdySjkShowInfo(@Param("username") String username);
}
