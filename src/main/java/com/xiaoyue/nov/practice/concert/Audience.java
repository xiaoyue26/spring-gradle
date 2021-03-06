package com.xiaoyue.nov.practice.concert;

import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

/**
 * Created by xiaoyue26 on 17/12/1.
 */
@Aspect
@Order(1)
public class Audience {


    @Pointcut("execution(* com.xiaoyue.nov.practice.concert.Performance.perform(..))")
    public void performance() {
    }

    @Before("performance()")
    public void silenceCellPhones() {
      System.out.println("--silencing cell phones");
    }

    @Before("performance()")
    public void takeSeates(){
        System.out.println("--taking seats");
    }

    @AfterReturning("performance()")
    public void applause(){
        System.out.println("CLAP CLAP CLAP!");
    }

    @AfterThrowing("performance()")
    public void demandRefund(){
        System.out.println("Demanding a refund");
    }
}
