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
    private final AmqpTemplate rabbitTemplate;

    @Autowired // 尽量使用构造函数注入
    public Producer(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send() {
        String msg = "hello " + new Date();
        System.out.println("Sender : " + msg);
        // 只指定了routing key , 默认转发到名为""的exchange,然后它默认会轮询所有queue是否接受.
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_HELLO, msg);
        //指定exchange和routingkey
        //this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.QUEUE_HELLO, msg);
    }
}
