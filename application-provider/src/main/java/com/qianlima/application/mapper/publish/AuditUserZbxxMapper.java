package com.qianlima.application.mapper.publish;


import com.qianlima.application.domain.publish.AuditUserZbxx;
import com.qianlima.application.domain.publish.ZdyUserZbxx;

import java.util.List;

public interface AuditUserZbxxMapper {
    Integer insertAuditUserZbxx(AuditUserZbxx auditUserZbxx);

    Integer insertAuditUserZbxxContent(ZdyUserZbxx zdyUserZbxx);

    Integer insertAuditUserZbxxLog(AuditUserZbxx auditUserZbxx);

    Integer updateAuditUserZbxx(AuditUserZbxx auditUserZbxx);

    Integer updateAuditUserZbxxContent(ZdyUserZbxx zdyUserZbxx);

    Integer deleteAuditUserZbxx(Integer id);

    Integer deleteAuditUserZbxxContent(Integer id);

    Integer selectCountByUserid(Long userid);

    List<AuditUserZbxx> selectAllByUserid(Long userid,Integer pageNo,Integer pageSize);

    AuditUserZbxx selectOneById(Integer id);

    String selectContentById(Integer id);
}