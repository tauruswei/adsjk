package org.cos.common.config;

import org.cos.common.exception.GlobalException;
import org.cos.common.result.CodeMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class CoresFilter implements Filter {
    @Value("${spring.myapp.allowedOrigins}")
    private String domain;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)  {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String origin = request.getHeader("origin");
        if(Arrays.asList(domain.split(",")).contains(origin)){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With");
        }else{
            throw  new GlobalException(CodeMsg.SERVER_ORIGIN_ERROR);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }
}