//package org.cos.common.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * AJAX请求跨域
// * @author Mr.W
// * @time 2018-08-13
// */
//@Configuration
//public class CorsConfig extends WebMvcConfigurerAdapter {
//    static final String ORIGINS[] = new String[]{"GET", "POST", "PUT", "DELETE"};
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "OPTIONS")
//                .allowedHeaders("Accept", "Authorization", "Cache-Control", "Content-Type", "DNT", "If-Modified-Since", "Keep-Alive", "Origin", "User-Agent", "X-Mx-ReqToken", "X-Requested-With")
//                .allowCredentials(true)
//                .maxAge(86400);
//    }
//}