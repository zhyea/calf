package org.chobit.calf.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author robin
 */
@Component
@ConfigurationProperties(prefix = "calf")
public class CalfConfig {


    private String theme;


    public String getThemePath() {
        return "themes/" + theme + "/";
    }

    /*---------*/

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
