package com.qianlima.application.service.projectattention;

import com.qianlima.application.dto.projectattention.MakeAttentionDto;
import com.qianlima.application.dto.projectattention.ProjectAttentionUsageDto;
import com.qianlima.base.response.PageBase;

/**
 * @author ZHangYJ
 */
public interface ProjectAttentionService {

    /**
     * 查询项目专耵功能使用情况
     * @param userId 用户id
     * @return 项目专耵功能使用情况
     */
    ProjectAttentionUsageDto getUsage(Integer userId);

    /**
     * 项目专耵
     * @param makeAttentionDto {@link MakeAttentionDto}
     */
    void makeAttention(MakeAttentionDto makeAttentionDto);

    /**
     * 获取用户专耵的信息
     * @param userId 用户
     * @param projectId 项目id
     * @return 专耵的信息
     */
    MakeAttentionDto getAttention(Integer userId, Integer projectId);

    /**
     * 获取专耵列表
     * @param userId 用户
     * @param pageNo pageNo
     * @param pageSize pageSize
     * @return PageBase<MakeAttentionDto>
     */
    PageBase<MakeAttentionDto> getAttentionByUserId(Integer userId, Integer pageNo, Integer pageSize);

    /**
     * 取消项目专耵, status设置为2
     * @param userId 用户id
     * @param id 专耵表主键
     * @return 项目专耵信息
     */
    MakeAttentionDto cancel(Integer userId, Integer id);
}
