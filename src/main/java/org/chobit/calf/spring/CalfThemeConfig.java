package org.chobit.calf.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import static org.chobit.calf.constants.Config.PATH_ADMIN;

/**
 * @author robin
 */
@Component
public class CalfThemeConfig implements InitializingBean {

    @Value("${calf.theme}")
    private String theme;

    private SpringResourceTemplateResolver templateResolver;

    public CalfThemeConfig(SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    public String getThemePath() {
        return "themes/" + theme + "/";
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        templateResolver.addTemplateAlias("admin.header", PATH_ADMIN + "common/header");
        templateResolver.addTemplateAlias("admin.nav", PATH_ADMIN + "common/navigator");
        templateResolver.addTemplateAlias("admin.alert", PATH_ADMIN + "common/alert");
        templateResolver.addTemplateAlias("admin.footer", PATH_ADMIN + "common/footer");
    }
}
