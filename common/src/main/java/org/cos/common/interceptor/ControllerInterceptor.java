package org.cos.common.interceptor;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/2/27 0027 17:23
 */
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(10)
public class ControllerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ControllerInterceptor.class);

    @Pointcut("execution(* org.cos.application.controller.*.*(..))")
    private void controllerAspect() {}

    @Around("controllerAspect()")
    public Object loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object resultData = null;
        Object[] args = joinPoint.getArgs();
//        Object apiName = args[0];
        String methodName = joinPoint.getSignature().getName();
//        try {
            log.info("======>请求--{}--接口开始,参数:{}", methodName, args);
            resultData = joinPoint.proceed(args);
            long endTime = System.currentTimeMillis();
            log.info("======>请求--{}--接口完成,耗时:{}ms,返回:{}", new Object[] { methodName, Long.valueOf(endTime - startTime), JSON.toJSON(resultData) });
//        }
//        catch (Throwable e) {
//            long endTime = System.currentTimeMillis();
//            log.error("======>请求--{}--接口异常！耗时:{}ms", methodName, Long.valueOf(endTime - startTime));
//
//        }
        return resultData;
    }
}