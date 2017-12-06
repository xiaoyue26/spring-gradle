package com.xiaoyue.nov.configure;

import com.xiaoyue.nov.practice.mq.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiaoyue26 on 17/12/5.
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String QUEUE_HELLO = "hello";

    @Bean
    public Queue helloQueue() {
        return new Queue(QUEUE_HELLO);
    }

 /* private final String EXCHANGE_NAME = "exchange_name";
    public final String QUEUE_NAME = "hello";

    @Autowired
    private ConnectionFactory connectionFactory;*/


    /*@Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate tmp = new RabbitTemplate(connectionFactory);
        //tmp.setExchange(EXCHANGE_NAME);
        //tmp.setQueue(QUEUE_NAME);
        return tmp;
    }*/

  /*  @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
*/
    /*
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }*/

}
