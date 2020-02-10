package org.chobit.calf.web;

import org.springframework.ui.ModelMap;

import static org.chobit.calf.constants.Constants.REDIRECT_PREFIX;
import static org.chobit.calf.utils.Strings.isNotBlank;

/**
 * @author robin
 */
public abstract class AbstractPageController {


    public String forward(String viewName, ModelMap map, String title) {
        if (isNotBlank(title)) {
            title = themeName() + title;
        } else {
            title = themeName();
        }

        map.put("title", title);
        return viewName;
    }

    /**
     * 跳转
     *
     * @param viewName 视图名称
     */
    public String redirect(String viewName) {
        return REDIRECT_PREFIX + viewName;
    }


    protected abstract String themeName();
}
