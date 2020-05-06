package com.qianlima.application.service.impl.projectattention;

import com.github.pagehelper.Page;
import com.qianlima.application.domain.qianlima.ZdyXmZd;
import com.qianlima.application.domain.qianlima.ZdyXmZdWithBLOBs;
import com.qianlima.application.domain.qlmservice.XSSelledInfo;
import com.qianlima.application.domain.qlmservice.ZdyCustomerToxm;
import com.qianlima.application.dto.projectattention.MakeAttentionDto;
import com.qianlima.application.dto.projectattention.ProjectAttentionUsageDto;
import com.qianlima.application.exception.ApplicationExceptionCode;
import com.qianlima.application.exception.MyApplicationException;
import com.qianlima.application.manage.transfer.MakeAttentionDtoTransfer;
import com.qianlima.application.mapper.qianlima.ZdyXmZdMapper;
import com.qianlima.application.mapper.qlmservice.XSSelledInfoMapper;
import com.qianlima.application.mapper.qlmservice.ZdyCustomerToxmMapper;
import com.qianlima.application.service.projectattention.ProjectAttentionService;
import com.qianlima.base.response.PageBase;
import org.apache.dubbo.config.annotation.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目专盯
 * @author ZHangYJ
 */
@Service
public class ProjectAttentionServiceImpl implements ProjectAttentionService {

    private final ZdyCustomerToxmMapper zdyCustomerToxmMapper;
    private final XSSelledInfoMapper xsSelledInfoMapper;
    private final ZdyXmZdMapper zdyXmZdMapper;

    public ProjectAttentionServiceImpl(ZdyCustomerToxmMapper zdyCustomerToxmMapper,
                                       XSSelledInfoMapper xsSelledInfoMapper,
                                       ZdyXmZdMapper zdyXmZdMapper) {
        this.zdyCustomerToxmMapper = zdyCustomerToxmMapper;
        this.xsSelledInfoMapper = xsSelledInfoMapper;
        this.zdyXmZdMapper = zdyXmZdMapper;
    }

    @Override
    public ProjectAttentionUsageDto getUsage(Integer userId){
        int useTotal = zdyXmZdMapper.countByUserId(userId);
        ZdyCustomerToxm zdyCustomerToxm = canAttention(userId, useTotal);
        return new ProjectAttentionUsageDto(zdyCustomerToxm.getTotalcount(), useTotal);
    }

    private ZdyCustomerToxm canAttention(Integer userId, int useTotal){

        ZdyCustomerToxm zdyCustomerToxm = zdyCustomerToxmMapper.selectByUserId(userId);
        // 老用户默认可以专盯一次 下表有记录可以送一条专耵
        if(zdyCustomerToxm == null){
            zdyCustomerToxm = giftOne(userId);
        }
        if(zdyCustomerToxm == null){
            throw new MyApplicationException(ApplicationExceptionCode.PROJECT_ATTENTION_NO_AUTH_ERROR);
        }
        // 过了有效期
        if(zdyCustomerToxm.getValidatetime() <
                LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8))){
            throw new MyApplicationException(ApplicationExceptionCode.PROJECT_ATTENTION_NO_AUTH_ERROR);
        }
        if(zdyCustomerToxm.getTotalcount() <= useTotal){
            throw new MyApplicationException(ApplicationExceptionCode.PROJECT_ATTENTION_DEPLETE_ERROR);
        }
        return zdyCustomerToxm;
    }

    public ZdyCustomerToxm giftOne(Integer userId){

        XSSelledInfo xsSelledInfo = xsSelledInfoMapper.selectByCid(userId);
        if (xsSelledInfo != null) {
            ZdyCustomerToxm zdyCustomerToxm = new ZdyCustomerToxm();
            zdyCustomerToxm.setTotalcount(1);
            zdyCustomerToxm.setValidatetime(xsSelledInfo.getTotime() + 86400);
            zdyCustomerToxm.setUserid(userId);
            zdyCustomerToxm.setInttime((int)(Instant.now().toEpochMilli() / 1000));
            zdyCustomerToxmMapper.insert(zdyCustomerToxm);
            return zdyCustomerToxm;
        }
        return null;
    }

    @Override
    public void makeAttention(MakeAttentionDto makeAttentionDto){

        // 查询使用次数
        int useTotal = zdyXmZdMapper.countByUserId(makeAttentionDto.getUserId());

        //校验有效期和条数 SELECT  validatetime,totalcount FROM  qlmservice.zdy_customer_toxm WHERE userid=?
        // 查询使用条数 select count(1) from qianlima.zdy_xm_zd where status <2 AND uid=
        canAttention(makeAttentionDto.getUserId(), useTotal);

        int now = (int)LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        ZdyXmZdWithBLOBs zdyXmZdWithBLOBs = zdyXmZdMapper.selectByUserIdAndContentId(makeAttentionDto.getUserId(), makeAttentionDto.getProjectId());
        if (zdyXmZdWithBLOBs == null) {

            zdyXmZdWithBLOBs = MakeAttentionDtoTransfer.MAPPER.toZdyXmZdWithBLOBs(makeAttentionDto);
            zdyXmZdWithBLOBs.setStatus(0);
            zdyXmZdWithBLOBs.setAddtime(now);
            zdyXmZdWithBLOBs.setUpdatetime(now);
            zdyXmZdMapper.insert(zdyXmZdWithBLOBs);
        } else if(zdyXmZdWithBLOBs.getStatus() >= 2){

            zdyXmZdWithBLOBs.setStatus(0);
            zdyXmZdWithBLOBs.setUpdatetime(now);
            zdyXmZdWithBLOBs.setmText(makeAttentionDto.getText());
            zdyXmZdWithBLOBs.setId(zdyXmZdWithBLOBs.getId());
            zdyXmZdMapper.updateByPrimaryKeySelective(zdyXmZdWithBLOBs);
        }
    }

    @Override
    public MakeAttentionDto getAttention(Integer userId, Integer projectId){
        ZdyXmZdWithBLOBs zdyXmZdWithBLOBs = zdyXmZdMapper.selectByUserIdAndContentId(userId, projectId);
        return MakeAttentionDtoTransfer.MAPPER.fromZdyXmZdWithBLOBs(zdyXmZdWithBLOBs);
    }

    @Override
    public PageBase<MakeAttentionDto> getAttentionByUserId(Integer userId, Integer pageNo, Integer pageSize){

        Page<ZdyXmZd> zdyXmZdPage = zdyXmZdMapper.selectByUserId(userId, pageNo, pageSize);
        List<MakeAttentionDto> makeAttentionDtoList = zdyXmZdPage.getResult().stream().map(MakeAttentionDtoTransfer.MAPPER::fromZdyXmZd).collect(Collectors.toList());
        return new PageBase<>(makeAttentionDtoList, zdyXmZdPage.getTotal(), zdyXmZdPage.getPageNum(), zdyXmZdPage.getPageSize());
    }

    @Override
    public MakeAttentionDto cancel(Integer userId, Integer id) {
        ZdyXmZd zdyXmZd = zdyXmZdMapper.selectByPrimaryKey(id);
        if(zdyXmZd != null){
            // 检查是不是本人的专耵记录
            if (zdyXmZd.getUid().equals(userId)) {
                if(zdyXmZd.getStatus() == 2){
                    return MakeAttentionDtoTransfer.MAPPER.fromZdyXmZd(zdyXmZd);
                }
                ZdyXmZdWithBLOBs zdyXmZdWithBLOBs = new ZdyXmZdWithBLOBs();
                zdyXmZdWithBLOBs.setStatus(2);
                zdyXmZdWithBLOBs.setId(id);
                if(zdyXmZdMapper.updateByPrimaryKeySelective(zdyXmZdWithBLOBs) == 0){
                    throw new MyApplicationException(ApplicationExceptionCode.PROJECT_ATTENTION_CANCEL_ERROR);
                }
                zdyXmZd.setStatus(2);
                return MakeAttentionDtoTransfer.MAPPER.fromZdyXmZd(zdyXmZd);
            }
        }
        return null;
    }
}
