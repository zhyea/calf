package org.chobit.calf.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import java.util.Map;

import static org.chobit.calf.constants.Config.PATH_ADMIN;

/**
 * @author robin
 */
@Component
@ConfigurationProperties(prefix = "calf")
public class CalfThemeConfig implements InitializingBean {

    private String theme;

    private Map<String, String> fragments;

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


        templateResolver.addTemplateAlias("header", getThemePath() + "common/header");
        templateResolver.addTemplateAlias("nav", getThemePath() + "common/navigator");
        templateResolver.addTemplateAlias("footer", getThemePath() + "common/footer");

        if (null != fragments) {
            for (Map.Entry<String, String> e : fragments.entrySet()) {
                templateResolver.addTemplateAlias(e.getKey(), getThemePath() + e.getValue());
            }
        }
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Map<String, String> getFragments() {
        return fragments;
    }

    public void setFragments(Map<String, String> fragments) {
        this.fragments = fragments;
    }
}
