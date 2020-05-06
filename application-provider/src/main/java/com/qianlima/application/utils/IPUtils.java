package com.qianlima.application.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Young
 * @Date: 2019/5/28 11:45
 */
public class IPUtils {

    private final static Logger logger = LoggerFactory.getLogger(IPUtils.class);

    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if ((ip == null) || ("null".equals(ip)) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("X-Real-IP");
        }
        if ((ip == null) || ("null".equals(ip)) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-ip_LOGs");
        }

        if ((ip == null) || ("null".equals(ip)) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-ip_LOGs");
        }
        logger.info("----------------ip-----------------:{}", ip);
        if ((ip == null) || ("null".equals(ip)) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }

        ip = ip.split(",")[0];
        if ("192.168.1.115".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
