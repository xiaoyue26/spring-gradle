package com.xiaoyue.nov.practice.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xiaoyue26 on 17/12/5.
 * 配置里设定监听queue2
 */
@Component
public class ConsumerPojo {

    private CountDownLatch latch = new CountDownLatch(1);//只需要计数一次的闭锁

    public void receiveMessage(String message) {
        System.out.println("[ConsumerPojo] Received <" + message + ">");
        latch.countDown();// 打开闭锁
    }

    public CountDownLatch getLatch() {
        return latch;//向外界返回闭锁
    }


}