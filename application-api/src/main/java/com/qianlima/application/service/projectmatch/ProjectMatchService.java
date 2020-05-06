package com.qianlima.application.service.projectmatch;

import com.qianlima.application.dto.projectmatch.ProjectMatchFeedbackDto;
import com.qianlima.application.dto.projectmatch.ProjectMatchRegulationDto;
import com.qianlima.application.dto.projectmatch.ProjectRecordDto;
import com.qianlima.base.response.PageBase;

/**
 * @author hanyuan.yu
 */
public interface ProjectMatchService {

    /**
     * 获取用户项目匹配规则
     *
     * @param userId 用户id
     * @return 项目匹配规则记录
     */
    ProjectMatchRegulationDto getProjectMatchRegulation(Long userId);

    /**
     * 创建项目匹配规则
     *
     * @param projectMatchRegulationDto
     */
    void insertOrUpdateProjectMatchRegulation(ProjectMatchRegulationDto projectMatchRegulationDto);

    /**
     * 添加项目匹配反馈
     *
     * @param projectMatchFeedbackDto
     */
    void insertProjectMatchFeedback(ProjectMatchFeedbackDto projectMatchFeedbackDto);

    /**
     * 获取项目匹配列表
     *
     * @param userId   用户id
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return
     */
    PageBase<ProjectRecordDto> getProjectMatchRecord(Integer userId, Integer pageNo, Integer pageSize);
}
