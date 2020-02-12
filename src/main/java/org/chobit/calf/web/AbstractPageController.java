package org.chobit.calf.web;

import org.springframework.ui.ModelMap;

import static org.chobit.calf.constants.Constants.REDIRECT_PREFIX;
import static org.chobit.calf.utils.Strings.isNotBlank;

/**
 * @author robin
 */
public abstract class AbstractPageController {


    /**
     * 跳转到指定视图，并设置页面title信息
     */
    public String view(String viewName, ModelMap map, String title) {
        if (isNotBlank(title)) {
            title = title + " - " + titleParent();
        } else {
            title = titleParent();
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



    /**
     * 网页title前缀
     */
    protected abstract String titleParent();
}
