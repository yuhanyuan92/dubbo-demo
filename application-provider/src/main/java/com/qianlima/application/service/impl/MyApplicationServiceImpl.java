package com.qianlima.application.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qianlima.application.domain.qianlima.ZdySjkShow;
import com.qianlima.application.dto.MyApplicationInfo;
import com.qianlima.application.exception.MyApplicationException;
import com.qianlima.application.mapper.qianlima.ZdySjkShowMapper;
import com.qianlima.application.po.MyApplicationData;
import com.qianlima.application.service.MyApplicationService;
import com.qianlima.application.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service(retries = 0, timeout = 2_000)
@Slf4j
public class MyApplicationServiceImpl implements MyApplicationService {

    @Value("${findConnections.url}")
    private String findConnectionsUrl;
    @Autowired
    private ZdySjkShowMapper zdySjkShowMapper;

    @Override
    public List<MyApplicationInfo> selectMyApplicationInfo(Long id, String username) {
        // 查询我的应用 -- 获取暂时固定不变的应用
        List<MyApplicationInfo> myApplicationInfoList = MyApplicationData.getFixedApplication();
        // 数据库应用
        ZdySjkShow zdySjkShow = zdySjkShowMapper.selectZdySjkShowInfo(username);
        if (zdySjkShow != null){
            log.info("数据库应用信息：{}", JSONObject.toJSONString(zdySjkShow));
            // 到期时间
            Long validtime = zdySjkShow.getValidtime();
            // 开通状态 -- 企业库
            int qysjk = zdySjkShow.getQysjk();
            // 开通状态 -- 设计库
            int sjsjk = zdySjkShow.getSjsjk();
            if (qysjk==1 || sjsjk==1){
                if (validtime > Instant.now().getEpochSecond()){
                    MyApplicationInfo dbApplication = new MyApplicationInfo();
                    dbApplication.setId(6);
                    dbApplication.setAppName("数据库");
                    dbApplication.setAppIcon("/img/app_01.6380c90.png");
                    dbApplication.setAppUrl("/userApplication_Database.html");
                    dbApplication.setIsShow(1);
                    myApplicationInfoList.add(dbApplication);
                }
            }
        }

        // EDM邮件
        /*MyApplicationInfo edmEmailApplication = new MyApplicationInfo();
        edmEmailApplication.setId(7);
        edmEmailApplication.setAppName("EDM邮件");
        edmEmailApplication.setAppIcon("http://vip-dev.qianlima.com/img/app_08.d4fd9f3.png");
        edmEmailApplication.setAppUrl("http://vip-dev.qianlima.com/userApplication_EDM.html");
        edmEmailApplication.setIsShow(1);
        myApplicationInfoList.add(edmEmailApplication);*/

        // 找人脉
        MyApplicationInfo netWorkApplication = new MyApplicationInfo();
        netWorkApplication.setId(8);
        netWorkApplication.setAppName("找人脉");
        netWorkApplication.setAppIcon("/img/app_06.40f71e7.png");
        netWorkApplication.setAppUrl("/userApplication_Connections.html");
        netWorkApplication.setIsShow(1);
        myApplicationInfoList.add(netWorkApplication);
//        String url  = String.format(findConnectionsUrl, id);
        // 调用url
        /*try {
            log.info("找人脉请求url:{}",url);
            String s = HttpRequestUtil.sendHttpGet(url);
            log.info("找人脉响应结果:{}",s);
            if (s!=null){
                JSONObject jsonObject = JSONObject.parseObject(s);
                if (jsonObject!=null){
                    JSONObject data = jsonObject.getJSONObject("data");
                    if (data!=null){
                        Integer surplusCount = data.getInteger("surpluscount");
                        Date endtime = data.getSqlDate("endtime");

                        if (surplusCount != null && surplusCount>0){

                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new MyApplicationException("找人脉请求失败,请重试");
        }*/
        return myApplicationInfoList;
    }


}
