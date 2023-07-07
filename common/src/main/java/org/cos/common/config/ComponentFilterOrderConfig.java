package org.cos.common.config;

import org.cos.common.filter.GetIpAddrFilter;
import org.cos.common.filter.TokenFilter;
import org.cos.common.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@KeepAll
@Configuration("filterconfig")
public class ComponentFilterOrderConfig {
    @Bean
    public Filter MyHiddenHttpMethodFilter(){
        return new GetIpAddrFilter();//自定义的过滤器
    }
    @Bean
    public Filter tokenAuthorFilter(){
        return new TokenFilter();//自定义的过滤器
    }
    @Bean
    public Filter xssFilter(){
        return new XssFilter();
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean1(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(MyHiddenHttpMethodFilter());
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
        filterRegistrationBean.addUrlPatterns("/certificate/*", "/localCA/*");
        filterRegistrationBean.setOrder(7);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean3(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(xssFilter());
        filterRegistrationBean.setName("XssFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(8);
        return filterRegistrationBean;
    }
}
