package org.cos.common.config;

import org.cos.common.filter.ExceptionHandlerFilter;
import org.cos.common.filter.GetIpAddrFilter;
import org.cos.common.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@KeepAll
@Configuration("filterconfig")
public class ComponentFilterOrderConfig {
    @Bean
    public Filter ipFilter(){
        return new GetIpAddrFilter();//自定义的过滤器
    }
    @Bean
    public Filter tokenAuthorFilter(){
        return new TokenFilter();//自定义的过滤器
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean1(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(ipFilter());
        filterRegistrationBean.setName("IpFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(6);//order的数值越小 则优先级越高
        return filterRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean2(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(tokenAuthorFilter());
        filterRegistrationBean.setName("TokenFilter");
//        filterRegistrationBean.addUrlPatterns("/user/*", "/certificate/*", "/localCA/*");
        filterRegistrationBean.addUrlPatterns("/user/logout", "/localCA/*");
        filterRegistrationBean.setOrder(7);
        return filterRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilter() {
        FilterRegistrationBean<ExceptionHandlerFilter> registrationBean = new FilterRegistrationBean<>();
        ExceptionHandlerFilter filter = new ExceptionHandlerFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(Integer.MAX_VALUE); // 设置为最后一个 Filter
        return registrationBean;
    }
}
