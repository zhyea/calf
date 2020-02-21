package org.chobit.calf.spring.ext;

import org.chobit.calf.service.entity.User;
import org.chobit.calf.tools.SessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SessionInterceptor implements HandlerInterceptor {


    private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        if (uri.startsWith("/admin") || uri.startsWith("/api")) {
            if (uri.startsWith("/admin/static")) {
                return true;
            }
            SessionHolder.add(request);
            Object user = SessionHolder.getAttribute("user");
            if (user instanceof User) {
                return true;
            }
            try {
                response.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else if (uri.startsWith("/login")) {
            SessionHolder.add(request);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
