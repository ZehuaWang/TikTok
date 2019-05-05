package com.imooc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //设置访问所有的静态资源
        registry.addResourceHandler("/**")
                .addResourceLocations("file:/Users/apple/Desktop/scala/TikTok/imoc-user-file/");
    }
}