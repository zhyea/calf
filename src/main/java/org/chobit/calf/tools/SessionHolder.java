package org.chobit.calf.tools;

import org.chobit.calf.model.AlertMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 线程上下文维护工具，主要是通过ThreadLocal维护访客信息
 *
 * @author robin
 */
public abstract class SessionHolder {

    private static final Logger logger = LoggerFactory.getLogger(SessionHolder.class);

    private static final ThreadLocal<HttpSession> SESSION_HOLDER = new ThreadLocal<>();


    public static void add(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.isNew() || null == SESSION_HOLDER.get()) {
            SESSION_HOLDER.set(request.getSession());
        }
    }


    public static HttpSession get() {
        return SESSION_HOLDER.get();
    }


    public static void addAlert(AlertMessage alertMessage) {
        HttpSession session = get();
        if (null != session && null == session.getAttribute("alert")) {
            addAttribute("alert", alertMessage);
        }
    }


    public static AlertMessage takeAlert() {
        return SessionHolder.takeAttribute("alert");
    }


    public static <T> void addAttribute(String attributeName, T value) {
        HttpSession session = get();
        if (null != session) {
            try{
                session.setAttribute(attributeName, value);
            }catch(IllegalStateException e){
                SESSION_HOLDER.remove();
            }
        }
    }


    public static <T> T takeAttribute(String attributeName) {
        HttpSession session = get();
        if (null != session) {
            try {
                Object obj = session.getAttribute(attributeName);
                if (null != obj) {
                    session.removeAttribute(attributeName);
                    return (T) obj;
                }
            } catch (IllegalStateException ise) {
                SESSION_HOLDER.remove();
            }

        }
        return null;
    }


    private SessionHolder() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }
}