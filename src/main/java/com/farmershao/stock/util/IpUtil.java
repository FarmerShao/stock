package com.farmershao.stock.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Zhao Da
 * @since : 2019/3/14 09:15
 */
public class IpUtil {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        String[] ipArray = ip.split(",");
        int var4 = ipArray.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String s = ipArray[var5];
            s = s.trim();
            if (!s.startsWith("10.") && !s.startsWith("192.")) {
                return s;
            }
        }

        if (StringUtils.isNotEmpty(ip)) {
            ip = ip.split(",")[0];
        }

        return ip;
    }
}
