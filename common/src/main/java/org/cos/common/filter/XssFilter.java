package org.cos.common.filter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
@WebFilter(filterName = "xssfilter")
public class XssFilter implements Filter {
    public XssFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String contentType = req.getContentType();
        if (StringUtils.isNotBlank(contentType)) {
            if (contentType.contains("application/json")) {
                String postContent = this.getBody(req);
                postContent = XssUtils.stripXss(postContent);
                postContent = StringEscapeUtils.unescapeHtml(postContent);
                request = new XssBodyRequestWrapper(req, postContent);
            } else if ((contentType.contains("application/x-www-form-urlencoded") || contentType.contains("multipart/form-data")) && !request.getParameterMap().isEmpty()) {
                request = new XssHttpServletRequestWrapper(req);
            }
            chain.doFilter(request, response);
        } else {
            chain.doFilter(req, response);
        }

    }
    private String getBody (ServletRequest request) throws IOException {
        return StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
    }
}
