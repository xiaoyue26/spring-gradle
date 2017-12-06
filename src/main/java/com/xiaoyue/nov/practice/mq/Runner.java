package com.xiaoyue.nov.practice.mq;

import com.xiaoyue.nov.configure.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoyue26 on 17/12/5.
 * 加载好applicationContext以后,自动运行的代码.
 * 打开@Component的话就会运行.
 */
//@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;
    private final ConsumerPojo consumer;

    @Autowired
    public Runner(RabbitTemplate rabbitTemplate, ConsumerPojo consumer) {
        this.rabbitTemplate = rabbitTemplate;
        this.consumer = consumer;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_2, "Hello from RabbitMQ!");
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}