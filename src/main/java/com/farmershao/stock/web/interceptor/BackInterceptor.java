package com.farmershao.stock.web.interceptor;

import com.farmershao.stock.config.CommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Zhao Da
 * @since : 2019/4/29 21:39
 */
@Component
public class BackInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CommonConfig commonConfig;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 请求后处理
        String remoteAddr = request.getHeader("Origin");
        String originIpStr = commonConfig.getJwtWhiteIp();
        String originIp = "";
        if (!StringUtils.isEmpty(originIpStr)) {
            String []originIps = originIpStr.split(",");
            for (String origin : originIps) {
                if (origin.contains(remoteAddr)) {
                    originIp = origin;
                    break;
                }
            }
        }
        response.setHeader("Access-Control-Allow-Origin", originIp);
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");

        response.setHeader("Access-Control-Expose-Headers", "Authorization,loginResult");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    }
}
