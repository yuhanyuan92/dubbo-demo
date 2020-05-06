package com.qianlima.application.service.impl.publishtender;

import com.qianlima.application.domain.publish.AuditUserZbxx;
import com.qianlima.application.domain.publish.ZdyUserZbxx;
import com.qianlima.application.dto.publishtender.AuditUserZbxxDto;
import com.qianlima.application.dto.publishtender.PublishDto;
import com.qianlima.application.mapper.publish.AuditUserZbxxMapper;
import com.qianlima.application.mapper.publish.ZdyUserZbxxMapper;
import com.qianlima.application.service.publishtender.PublishTenderService;
import com.qianlima.user.api.dto.ZdyMembersDto;
import com.qianlima.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发布招标
 */
@Service
@Slf4j
public class PublishTenderServiceImpl implements PublishTenderService {

    @Autowired
    private ZdyUserZbxxMapper zdyUserZbxxMapper;
    @Autowired
    private AuditUserZbxxMapper auditUserZbxxMapper;

    @Reference
    private UserService userService;

    @Override
    public Integer insertPublishTener(PublishDto publishDto) {
        ZdyUserZbxx zdyUserZbxx = new ZdyUserZbxx();
        BeanUtils.copyProperties(publishDto,zdyUserZbxx);
        zdyUserZbxx.setAddtime(System.currentTimeMillis()/1000);
        int result = zdyUserZbxxMapper.insertZdyUserZbxx(zdyUserZbxx);
        if (result == 0){
            log.info("插入 zdy_user_zbxx 失败 - -{}",zdyUserZbxx.toString());
            return 0;
        }
        AuditUserZbxx auditUserZbxx = new AuditUserZbxx();
        BeanUtils.copyProperties(publishDto,auditUserZbxx);
        auditUserZbxx.setAddtime(zdyUserZbxx.getAddtime());
        auditUserZbxx.setStatus(getIsorNOt(zdyUserZbxx.getTitle(),zdyUserZbxx.getContent()));
        auditUserZbxx.setId(zdyUserZbxx.getId());
        ZdyMembersDto allZdyMembersById = userService.getAllZdyMembersById(publishDto.getUserid());
        if (allZdyMembersById.getDengji()>5){
            auditUserZbxx.setMianfei(1);
        }else {
            auditUserZbxx.setMianfei(0);
        }
        result = auditUserZbxxMapper.insertAuditUserZbxx(auditUserZbxx);
        if (result == 0){
            log.info("插入 audit_user_zbxx 失败 - -{}",auditUserZbxx.toString());
            return 0;
        }
        result = auditUserZbxxMapper.insertAuditUserZbxxContent(zdyUserZbxx);
        if (result == 0){
            log.info("插入 audit_user_zbxx_content 失败 - -{}",zdyUserZbxx.toString());
            return 0;
        }
        result = auditUserZbxxMapper.insertAuditUserZbxxLog(auditUserZbxx);
        if (result == 0){
            log.info("插入 audit_user_zbxx_log 失败 - -{}",auditUserZbxx.toString());
            return 0;
        }
        return result;
    }

    @Override
    public Integer updatePublishTender(PublishDto publishDto) {
        ZdyUserZbxx zdyUserZbxx = new ZdyUserZbxx();
        BeanUtils.copyProperties(publishDto,zdyUserZbxx);
        zdyUserZbxx.setAddtime(System.currentTimeMillis()/1000);
        int result = zdyUserZbxxMapper.updateZdyUserZbxx(zdyUserZbxx);
        if (result == 0){
            log.info("更新 zdy_user_zbxx 失败 - -{}",zdyUserZbxx.toString());
            return 0;
        }
        AuditUserZbxx auditUserZbxx = new AuditUserZbxx();
        BeanUtils.copyProperties(publishDto,auditUserZbxx);
        auditUserZbxx.setAddtime(zdyUserZbxx.getAddtime());
        ZdyMembersDto allZdyMembersById = userService.getAllZdyMembersById(publishDto.getUserid());
        if (allZdyMembersById.getDengji()>5){
            auditUserZbxx.setMianfei(1);
        }else {
            auditUserZbxx.setMianfei(0);
        }
        result = auditUserZbxxMapper.updateAuditUserZbxx(auditUserZbxx);
        if (result == 0){
            log.info("更新 audit_user_zbxx 失败 - -{}",auditUserZbxx.toString());
            return 0;
        }
        result = auditUserZbxxMapper.updateAuditUserZbxxContent(zdyUserZbxx);
        if (result == 0){
            log.info("更新 audit_user_zbxx_content 失败 - -{}",zdyUserZbxx.toString());
            return 0;
        }
        return result;
    }

    @Override
    public Integer deletePublishTender(Integer id) {
        int result = zdyUserZbxxMapper.deleteZdyUserZbxx(id);
        if (result == 0){
            log.info("删除 zdy_user_zbxx 失败 - -{}",id);
            return 0;
        }
        result = auditUserZbxxMapper.deleteAuditUserZbxx(id);
        if (result == 0){
            log.info("删除 zdy_user_zbxx 失败 - -{}",id);
            return 0;
        }
        result = auditUserZbxxMapper.deleteAuditUserZbxxContent(id);
        if (result == 0){
            log.info("删除 audit_user_zbxx_content 失败 - -{}",id);
            return 0;
        }
        return result;
    }

    @Override
    public Map<String,Object> getDataByUserid(Long userid, Integer pageNo, Integer pageSize) {
        Integer total = auditUserZbxxMapper.selectCountByUserid(userid);
        List<AuditUserZbxx> auditUserZbxxes = auditUserZbxxMapper.selectAllByUserid(userid,pageNo,pageSize);
        List<AuditUserZbxxDto> auditUserZbxxDtos = new ArrayList<AuditUserZbxxDto>(){{
            auditUserZbxxes.forEach(auditUserZbxx -> {
                AuditUserZbxxDto auditUserZbxxDto = new AuditUserZbxxDto();
                BeanUtils.copyProperties(auditUserZbxx,auditUserZbxxDto);
                auditUserZbxxDto.setTime(auditUserZbxx.getAddtime());
                this.add(auditUserZbxxDto);
            });
        }};
        return new HashMap<String,Object>(2){{
            this.put("total",total);
            this.put("data",auditUserZbxxDtos);
        }};
    }

    @Override
    public PublishDto getDataById(Integer id) {
        AuditUserZbxx auditUserZbxx = auditUserZbxxMapper.selectOneById(id);
        PublishDto publishDto = new PublishDto();
        BeanUtils.copyProperties(auditUserZbxx,publishDto);
        publishDto.setContent(auditUserZbxxMapper.selectContentById(id));
        return publishDto;
    }

    /**
     * 验证是否为广告
     */
    private Integer getIsorNOt(String title,String content){
        String[] keyword={"项目","公告","预告","变更","比选","答疑","招标","采购","投标","中标","询价","报价","报名"};
        for(String s:keyword){
            if(title.contains(s) || content.contains(s)){
                return 0;
            }
        }
        return 7;
    }
}
