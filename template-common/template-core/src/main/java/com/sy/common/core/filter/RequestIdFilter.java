package com.sy.common.core.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Monster
 * @version v1.0
 */
@Component
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID = "X-Request-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            // 前置逻辑：生成并设置 requestId
            String uuid = UUID.randomUUID().toString().replace("-", "");
            MDC.put(REQUEST_ID, uuid);
        }
        try {
            // 继续过滤器链
            chain.doFilter(request, response);
        } finally {
            // 确保清理 MDC
            MDC.remove(REQUEST_ID);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
