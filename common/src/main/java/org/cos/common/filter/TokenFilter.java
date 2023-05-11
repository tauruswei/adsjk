package org.cos.common.filter;

import org.cos.common.exception.GlobalException;
import org.cos.common.result.CodeMsg;
import org.cos.common.token.CheckResult;
import org.cos.common.token.TokenManager;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Decription: 身份认证过滤器
 *
 * @author wangtao
 * create on 2017/12/28.
 */

@WebFilter(filterName = "tokenfilter", urlPatterns = { "/certificate/*", "/localCA/*"})
public class TokenFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("token Filter initialization");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		/*
		  1.执行接口调用需要进行身份认证检查token 合法性
		 */
		logger.debug("request url" + ((HttpServletRequest) request).getRequestURI());
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String auth = httpServletRequest.getHeader("authorization");

        if (StringUtils.isBlank(auth)) {
            //不存在token
            throw new GlobalException(CodeMsg.TOKEN_NOT_EXIST);
        }
        //验证token
        CheckResult checkResult = TokenManager.validateJWT(auth);
        Claims claim = checkResult.getClaims();


//		ManagePerson managePerson = (ManagePerson) JSON.toJSON(claim.get("operator"));
//        MDC.put("userName", claim.get("userName").toString());
//        MDC.put("cn",claim.get("cn").toString());
//        MDC.put("group_flag", claim.get("group_flag").toString());
//        MDC.put("uid", claim.get("uid").toString());
//        MDC.put("uCert", claim.get("uCert").toString());
//        MDC.put("udn", claim.get("udn").toString());

        //验证通过 执行后续操作
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("token Filter destruction");
    }
}
