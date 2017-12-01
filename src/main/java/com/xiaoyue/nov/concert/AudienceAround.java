package com.xiaoyue.nov.concert;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by xiaoyue26 on 17/12/1.
 * 用@Around注解,可能会阻塞对方
 */
@Aspect
public class AudienceAround {
    @Pointcut("execution(* com.xiaoyue.nov.concert.Performance.perform(..))")
    public void performance() {
    }

    @Around("performance()")
    public void watch(ProceedingJoinPoint jp){
        try {
            System.out.println("aaaa silencing cell phone ");
            System.out.println("aaaa taking seats ");
            jp.proceed();// 不调这个就阻塞了
            System.out.println("aaaa CLAP CLAP CLAP!");
        }
        catch (Throwable e){
            System.out.println("aaaa Demanding a refund");
        }
    }


}
