package com.xiaoyue.nov.configure;

import com.xiaoyue.nov.practice.mq.ConsumerPojo;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiaoyue26 on 17/12/5.
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String QUEUE_HELLO = "hello";
    public static final String QUEUE_2 = "queue2";
    public static final String EXCHANGE_NAME = "spring-boot-exchange";
    public static final String METHOD_NAME = "receiveMessage";

    @Bean
    public Queue helloQueue() {
        return new Queue(QUEUE_HELLO);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_2);
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean // 创建一条路由规则: 到达exchange以后,routingkey是queue2的,转发到queue2上.
    Binding binding(@Qualifier(QUEUE_2) Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_2);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ConsumerPojo consumerPojo) {//转换pojo
        return new MessageListenerAdapter(consumerPojo, METHOD_NAME);
    }

    @Bean
    SimpleMessageListenerContainer container(//装配好的listener自动会跑(不知道是哪里调用的)
            ConnectionFactory connectionFactory,// 自动配置
            MessageListenerAdapter listenerAdapter) {// 上面一个函数把pojo包装成listener
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_2);
        container.setMessageListener(listenerAdapter);
        return container;
    }


}
