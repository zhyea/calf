package org.chobit.calf.spring.ext;

import org.chobit.calf.model.Visitor;
import org.chobit.calf.service.entity.User;
import org.chobit.calf.tools.CalfTools;
import org.chobit.calf.tools.SessionHolder;
import org.chobit.calf.tools.VisitorHolder;
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
        if (uri.equals("/api/remote/work")) {
            return null != request.getHeader("code");
        } else if (uri.startsWith("/admin") || uri.startsWith("/api")) {
            if (uri.startsWith("/admin/static")) {
                return true;
            }
            if (null != request.getSession(false)) {
                SessionHolder.add(request);
                User user = SessionHolder.getUser(request);
                if (null != user) {
                    return true;
                }
            }
            try {
                response.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else if (uri.startsWith("/login")) {
            SessionHolder.add(request);
        } else {
            String ip = CalfTools.clientIp(request);
            String ua = request.getHeader("user-agent");
            Visitor visitor = new Visitor();
            visitor.setSpider(ua.contains("bot") || ua.contains("pider"));
            logger.debug("visitor info: ip:[{}], user-agent:[{}]", ip, ua);
            VisitorHolder.add(visitor);
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
