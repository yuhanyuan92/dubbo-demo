package com.qianlima.application.po;

import com.qianlima.application.dto.MyApplicationInfo;

import java.util.ArrayList;
import java.util.List;

public class MyApplicationData {
    public static List<MyApplicationInfo> getFixedApplication() {
        List<MyApplicationInfo> list = new ArrayList<>();
        MyApplicationInfo supplierApplication = new MyApplicationInfo();
        supplierApplication.setId(1);
        supplierApplication.setAppName("供应商认证");
        supplierApplication.setAppIcon("/img/app_07.d654094.png");
        supplierApplication.setAppUrl("/userApplication_Certification.html");
        supplierApplication.setIsShow(1);
        list.add(supplierApplication);

        MyApplicationInfo projectApplication = new MyApplicationInfo();
        projectApplication.setId(2);
        projectApplication.setAppName("项目专盯");
        projectApplication.setAppIcon("/img/app_03.069e957.png");
        projectApplication.setAppUrl("/userApplication_Ding.html");
        projectApplication.setIsShow(1);
        list.add(projectApplication);

        MyApplicationInfo monitorOtherApplication = new MyApplicationInfo();
        monitorOtherApplication.setId(3);
        monitorOtherApplication.setAppName("竞争对手监控");
        monitorOtherApplication.setAppIcon("/img/app_04.1ae993b.png");
        monitorOtherApplication.setAppUrl("/userApplication_Monitor.html");
        monitorOtherApplication.setIsShow(1);
        list.add(monitorOtherApplication);

        MyApplicationInfo publishTenderApplication = new MyApplicationInfo();
        publishTenderApplication.setId(4);
        publishTenderApplication.setAppName("发布招标");
        publishTenderApplication.setAppIcon("/img/app_05.0b4a033.png");
        publishTenderApplication.setAppUrl("/userApplication_AddTender.html");
        publishTenderApplication.setIsShow(1);
        list.add(publishTenderApplication);

        MyApplicationInfo intelligentDataPack = new MyApplicationInfo();
        intelligentDataPack.setId(5);
        intelligentDataPack.setAppName("智能数据包");
        intelligentDataPack.setAppIcon("/img/app_09.0c37378.png");
        intelligentDataPack.setAppUrl("/userApplication_DataBag.html");
        intelligentDataPack.setIsShow(1);
        list.add(intelligentDataPack);

        return list;
    }
}
