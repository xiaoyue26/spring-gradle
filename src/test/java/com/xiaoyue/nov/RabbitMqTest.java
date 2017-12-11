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
 * <p>
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
    public void hello() throws Exception {// 发到名为""的exchange,会尝试所有queue,因为routing key,只有queue_hello能收到
        sender.send();
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ConsumerPojo consumer;

    @Test
    public void hello2() throws Exception {
        System.out.println("Sending message...");
        // 发到名为""spring-boot-exchange"的exchange,
        // 根据配置中创建的路由,根据routingkey会转发到queue2.
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_2, "Hello from RabbitMQ!");
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }


}
