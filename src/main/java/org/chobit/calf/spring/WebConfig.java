package org.chobit.calf.spring;

import org.chobit.calf.spring.ext.CalfThemePageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author robin
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private CalfConfig calfConfig;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CalfThemePageInterceptor(calfConfig.getThemePath()));
    }


    /**
     * 配置静态文件
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("classpath:upload/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:templates/" + calfConfig.getThemePath() + "static/");
        registry.addResourceHandler("/admin/static/**")
                .addResourceLocations("classpath:templates/admin/static/");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }


}
