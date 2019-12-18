package com.serzh.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
class AddParamAspect {

    @Pointcut(value = "@annotation(com.serzh.aop.AddParam) && args(param)")
    void addParamPointcut(String param){}

    @Around(value = "addParamPointcut(param)", argNames = "joinPoint,param")
    Object addParam(ProceedingJoinPoint joinPoint , String param) throws Throwable {
       return joinPoint.proceed(new Object[] {param + "AndParam"});
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

}
