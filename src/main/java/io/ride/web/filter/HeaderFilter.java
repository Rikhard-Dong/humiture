package io.ride.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        res.setContentType("textml;charset=UTF-8");
        res.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "0");
        res.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("XDomainRequestAllowed", "1");

        HttpSession session = request.getSession();
        LOGGER.info("session user info = {}", session.getAttribute("user"));

//
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
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
