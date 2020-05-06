package com.qianlima.application.service.publishtender;


import com.qianlima.application.dto.publishtender.PublishDto;

import java.util.Map;

/**
 * 发布招标
 */
public interface PublishTenderService {

   Integer insertPublishTener(PublishDto publishDto);

   Integer updatePublishTender(PublishDto publishDto);

   Integer deletePublishTender(Integer id);

   Map<String,Object> getDataByUserid(Long userid, Integer pageNo, Integer pageSize);

   PublishDto getDataById(Integer id);

}
