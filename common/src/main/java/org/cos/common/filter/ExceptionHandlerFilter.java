package org.cos.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cos.common.result.CodeMsg;
import org.cos.common.tool.LogTool;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerFilter extends GenericFilterBean {
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {

            e.printStackTrace();
            CodeMsg codeMsg = CodeMsg.SERVER_ERROR;


            // 这里可以做一些异常处理，比如记录日志、生成特定的 HTTP 响应等
            // 示例：返回一个 HTTP 500 状态码和错误信息
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            // 分割异常信息，提取 code 和 msg,同时去除字符串两侧的[]
            String[] parts = e.getMessage().substring(1, e.getMessage().length() - 1).split(", ");
            int code = Integer.parseInt(parts[0].split("=")[1]);
            String msg = parts[1].split("=")[1];
            Map<String, Object> response = new HashMap<>();
            response.put("msg", msg);
            response.put("code", code);
            codeMsg.setCode(code);
            codeMsg.setMsg(msg);
            logger.error(LogTool.failLog(codeMsg));

            String jsonResponse = objectMapper.writeValueAsString(response);
            httpServletResponse.getWriter().write(jsonResponse);
            httpServletResponse.getWriter().flush();
        }
    }
}
