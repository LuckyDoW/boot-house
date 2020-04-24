package com.etoak.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * 配置支持同步（表单方式提交）请求rest请求过滤器
 * 作用：将post请求转成put，delete请求等
 * 要求：
 * 1、表单提交方式必须post
 * 2、表单必须有一个隐藏域  name属性值_method value属性值是rest请求方式（如put、delete.......）
 * @author DouDou、
 */
@Configuration
public class RestFilterConfig {
    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> registrationBean(){

        FilterRegistrationBean<HiddenHttpMethodFilter> registrationBean =
                new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
