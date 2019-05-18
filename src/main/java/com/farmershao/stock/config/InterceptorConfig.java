package com.farmershao.stock.config;


import com.farmershao.stock.web.interceptor.BackInterceptor;
import com.farmershao.stock.web.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterceptorConfig
 *  拦截器配置
 * @author Shao Yu
 * @since 2019/3/27 19:04
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Autowired
    private BackInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/back/**")
                .excludePathPatterns("/back/sysAdmin/login/**")
                .order(2);
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/back/**")
                .order(3);
    }
}
