package com.etoak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

}
