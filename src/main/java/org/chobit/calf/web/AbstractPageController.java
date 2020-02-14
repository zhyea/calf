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
        String t = titleParent();
        if (isNotBlank(title)) {
            t = title + " - " + titleParent();
        }

        map.put("title", t);
        return viewName;
    }

    /**
     * 跳转
     *
     * @param uri 跳转路径
     */
    public String redirect(String uri) {
        return REDIRECT_PREFIX + uri;
    }


    /**
     * 网页title前缀
     */
    protected abstract String titleParent();
}
