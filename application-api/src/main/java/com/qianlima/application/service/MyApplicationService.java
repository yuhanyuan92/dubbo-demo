package com.qianlima.application.service;

import com.qianlima.application.dto.MyApplicationInfo;

import java.util.List;

public interface MyApplicationService {
    List<MyApplicationInfo> selectMyApplicationInfo(Long id, String username);
}
