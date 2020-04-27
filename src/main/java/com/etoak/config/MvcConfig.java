package com.etoak.config;

import com.etoak.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 访问图片
 * 访问地址：http://localhost:8080/boot/数据库
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.mapping}")
    private String imgMapping;

    /**
     * d:/upload/
     */
    @Value("${upload.dir}")
    private String imgLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imgMapping)
                .addResourceLocations("file:" + imgLocation);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/code")   //不拦截验证码
                .excludePathPatterns("/lib/**","/imgs/**");

    }

    /**
     * mvc：view-controller
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //当访问controller时  跳转到index页面
        registry.addViewController("/")
                .setViewName("index");
    }
}
