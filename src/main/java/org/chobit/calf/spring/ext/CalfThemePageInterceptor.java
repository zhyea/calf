package org.chobit.calf.spring.ext;

import org.chobit.calf.web.admin.AdminPageController;
import org.chobit.calf.web.front.FrontPageController;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author robin
 */
public class CalfThemePageInterceptor implements HandlerInterceptor {


    private String themePath;

    private static final String PATH_ADMIN = "admin/";


    public CalfThemePageInterceptor(String themePath) {
        this.themePath = themePath;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           @Nullable ModelAndView modelAndView) {

        if (null == modelAndView || null == handler) {
            return;
        }

        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        Object m = ((HandlerMethod) handler).getBean();

        if (m instanceof FrontPageController) {
            modelAndView.setViewName(themePath + modelAndView.getViewName());
        }

        if (m instanceof AdminPageController) {
            modelAndView.setViewName(PATH_ADMIN + modelAndView.getViewName());
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {
    }

}
