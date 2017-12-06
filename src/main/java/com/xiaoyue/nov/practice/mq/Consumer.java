package com.xiaoyue.nov.practice.mq;

import com.xiaoyue.nov.configure.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoyue26 on 17/12/5.
 */
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_HELLO)
public class Consumer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String hello) {
        logger.info("Receiver : log " + hello);
        System.out.println("Receiver : " + hello);
    }
}
