package org.chobit.calf.spring;

import org.chobit.calf.service.UploadComponent;
import org.chobit.calf.spring.ext.CalfThemeInterceptor;
import org.chobit.calf.spring.ext.PseudoStaticPathHelper;
import org.chobit.calf.spring.ext.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author robin
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private CalfThemeConfig calfConfig;
    @Autowired
    private UploadComponent uploadComponent;


    private UrlPathHelper urlPathHelper = new PseudoStaticPathHelper();

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUrlPathHelper(urlPathHelper);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CalfThemeInterceptor(calfConfig.getThemePath()));
        registry.addInterceptor(new SessionInterceptor());
    }


    /**
     * 配置静态文件
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("classpath:upload/", uploadComponent.uploadPath());
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:templates/" + calfConfig.getThemePath() + "static/");
        registry.addResourceHandler("/admin/static/**")
                .addResourceLocations("classpath:templates/admin/static/");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error").setViewName("error");
        registry.addStatusController("/404", HttpStatus.NOT_FOUND);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }


}
