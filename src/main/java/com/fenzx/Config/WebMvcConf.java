package com.fenzx.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {
    @Autowired
    private ConfigInterceptor configInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(configInterceptor)
//
////
////                // .addPathPatterns() 是配置需要拦截的路径
//                .addPathPatterns("/**")
//////                 .excludePathPatterns("/**")// 用于排除拦截
////
//                .excludePathPatterns("/login") // 排除127.0.0.1进入登录页
//                .excludePathPatterns("/login.html") // 排除127.0.0.1进入登录页
//                .excludePathPatterns("/css/**") // 排除静态文件
//                .excludePathPatterns("/js/**")
//                .excludePathPatterns("/font.roboto/**")
//                .excludePathPatterns("/img/**")
//                .excludePathPatterns("/js/**")
//                .excludePathPatterns("/scss/**")
//                .excludePathPatterns("/src.js/**");
    }


}
