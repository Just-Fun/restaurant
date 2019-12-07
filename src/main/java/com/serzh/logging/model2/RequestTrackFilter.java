package com.serzh.logging.model2;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import static com.serzh.logging.Constants.X_SERZH_TRACKING_ID;

public class RequestTrackFilter implements Filter {
    private static final ThreadLocal<String> id = new ThreadLocal<>();

    public RequestTrackFilter() {
    }

    public static String getId() {
        return id.get();
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
//        String trackId = httpServletRequest.getHeader("x-serzh-tracking-id");
        String trackId = httpServletRequest.getHeader(X_SERZH_TRACKING_ID);
        id.set(StringUtils.isEmpty(trackId) ? UUID.randomUUID().toString() : trackId);
//        MDC.put("x-serzh-tracking-id", id.get());
        MDC.put(X_SERZH_TRACKING_ID, id.get());
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
