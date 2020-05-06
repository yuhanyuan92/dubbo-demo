package com.qianlima.application.mapper.qlmservice;

import com.qianlima.application.domain.qlmservice.ZdyVipMatchFeedback;

public interface ZdyVipMatchFeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZdyVipMatchFeedback record);

    int insertSelective(ZdyVipMatchFeedback record);

    ZdyVipMatchFeedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZdyVipMatchFeedback record);

    int updateByPrimaryKey(ZdyVipMatchFeedback record);

    ZdyVipMatchFeedback selectByContentId(Integer matchId, Integer contentId);
}