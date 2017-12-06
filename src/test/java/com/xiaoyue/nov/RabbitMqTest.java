package com.xiaoyue.nov;

import com.xiaoyue.nov.configure.RabbitMQConfig;
import com.xiaoyue.nov.practice.mq.ConsumerPojo;
import com.xiaoyue.nov.practice.mq.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoyue26 on 17/12/6.
 * 1. 先启动ReadingListApplication main函数,监听消息;
 * 2. 运行这个单元测试类,发送消息.
 *
 * 结果:
 * 1. main函数中打印接收到的消息;
 * 2. 单元测试成功退出.(也可以打印发送的消息,不过意义不大)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadingListApplication.class)
@WebAppConfiguration
public class RabbitMqTest {
    @Autowired
    private Producer sender;

    @Test
    public void hello() throws Exception {// queue hello,没有明确写binding和exchange,发了以后,都能收到.
        sender.send();
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ConsumerPojo consumer;
    @Test
    public void hello2() throws Exception{// 只有 queue2能收到.
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_2, "Hello from RabbitMQ!");
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }


}
