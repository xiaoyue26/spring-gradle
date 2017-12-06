package com.xiaoyue.nov.practice.mq;

import com.xiaoyue.nov.configure.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by xiaoyue26 on 17/12/5.
 */
@Component
public class Producer {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String msg = "hello " + new Date();
        System.out.println("Sender : " + msg);
        // 只指定了routing key , 自动转发到对应队列.
        this.rabbitTemplate.convertAndSend("hello", msg);
    }
}
