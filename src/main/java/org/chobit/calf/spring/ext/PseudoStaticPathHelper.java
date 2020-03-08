package org.chobit.calf.spring.ext;

import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.util.WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE;
import static org.springframework.web.util.WebUtils.INCLUDE_SERVLET_PATH_ATTRIBUTE;

/**
 * @author robin
 */
public class PseudoStaticPathHelper extends UrlPathHelper {

    private static final String SUFFIX = ".html";

    @Override
    public String getRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (!uri.endsWith(SUFFIX)) {
            return super.getRequestUri(request);
        }
        String path = (String) request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE);
        if (null == path) {
            int idx = uri.indexOf(SUFFIX);
            uri = uri.substring(0, idx);
            request.setAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE, uri);
            request.setAttribute(INCLUDE_SERVLET_PATH_ATTRIBUTE, uri);
        }

        return super.getRequestUri(request);
    }

}
