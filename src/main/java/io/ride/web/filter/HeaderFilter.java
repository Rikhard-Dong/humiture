package io.ride.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午8:47
 * 过滤器, 解决ajax跨域问题
 */
@WebFilter(filterName = "corsFilter", urlPatterns = "/*")
public class HeaderFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("过滤器启动, 解决跨域问题");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
//        response.setHeader("Content-Type", "application/json");
        LOGGER.info("请求URL --------------------------> {}", showUrl((HttpServletRequest) servletRequest));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String showUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public void destroy() {

    }
}
