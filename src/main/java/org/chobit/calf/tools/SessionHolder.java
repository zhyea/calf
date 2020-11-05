package org.chobit.calf.tools;

import org.chobit.calf.model.AlertMessage;
import org.chobit.calf.service.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 线程上下文维护工具，主要是通过ThreadLocal维护访客信息
 *
 * @author robin
 */
public final class SessionHolder {

    private static final Logger logger = LoggerFactory.getLogger(SessionHolder.class);


    public static final String KEY_USER = "user";
    private static final String KEY_ALERT = "alert";

    private static final ThreadLocal<HttpSession> SESSION_HOLDER = new ThreadLocal<>();


    public synchronized static void add(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null != session) {
            SESSION_HOLDER.set(session);
        }
    }

    public synchronized static void addDirectly(HttpSession session) {
        SESSION_HOLDER.set(session);
    }


    public synchronized static HttpSession get() {
        return SESSION_HOLDER.get();
    }


    public synchronized static void clear() {
        HttpSession session = get();
        if (null != session) {
            try {
                session.invalidate();
            } catch (IllegalStateException e) {
            }
        }
        SESSION_HOLDER.remove();
    }


    public synchronized static void addAlert(AlertMessage alertMessage) {
        HttpSession session = get();
        if (null != session && null == getAttribute(null, KEY_ALERT)) {
            addAttribute(KEY_ALERT, alertMessage);
        }
    }


    public synchronized static AlertMessage takeAlert() {
        return SessionHolder.takeAttribute(KEY_ALERT);
    }


    public synchronized static <T> void addAttribute(String attributeName, T value) {
        HttpSession session = get();
        if (null != session) {
            try {
                session.setAttribute(attributeName, value);
            } catch (IllegalStateException e) {
                SESSION_HOLDER.remove();
            }
        }
    }


    public synchronized static <T> T takeAttribute(String attributeName) {
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


    public synchronized static <T> T getAttribute(HttpSession session, String attributeName) {
        session = null == session ? get() : session;
        if (null != session) {
            try {
                Object obj = session.getAttribute(attributeName);
                if (null != obj) {
                    return (T) obj;
                }
            } catch (IllegalStateException ise) {
                SESSION_HOLDER.remove();
            }

        }
        return null;
    }


    public synchronized static User getUser() {
        return getAttribute(null, KEY_USER);
    }


    public synchronized static User getUser(HttpServletRequest request) {
        User user = getUser();
        if (null != user) {
            return user;
        } else {
            HttpSession session = request.getSession();
            Object obj = getAttribute(session, KEY_USER);
            if (obj instanceof User) {
                SessionHolder.addDirectly(session);
                return (User) obj;
            }
        }
        return null;
    }


    public synchronized static void addUser(HttpServletRequest request, User user) {
        addDirectly(request.getSession());
        addAttribute(KEY_USER, user);
    }


    public synchronized static User removeUser() {
        User user = takeAttribute(KEY_USER);
        SESSION_HOLDER.remove();
        return user;
    }


    private SessionHolder() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }
}