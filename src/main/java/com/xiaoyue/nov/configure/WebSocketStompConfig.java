package com.xiaoyue.nov.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by xiaoyue26 on 17/12/8.
 * 客户端逻辑:
 * 1. 创建到 /marcopolo的socket
 * 2. 用socket创建一个stomp;
 * 3. 连接stomp,用户名密码guest,订阅里面的/topic/marco,收到消息就调handlePolo;
 * 4. 向stomp的/app/marco发一条消息.
 * 5. 收到消息后: 打印出来,如果消息是Polo,就又向服务端发消息.
 * <p>
 * 总之:
 * 读: /app/marco
 * 写: /topic/marco
 * <p>
 * 服务端逻辑:
 * 1. 注册到 /marcopolo的stomp;
 * 2. 发给客户端的消息地址加上 /app前缀;
 * 3. 配置代理接收消息的前缀是 /queue和/topic.
 * // controller:
 * 4. 去掉前缀后是 /marco的消息, 接到后打印出来, 2秒后回复 'Polo';
 * 5. 每隔10秒, 发送一条随机数到  /topic/marco.
 * <p>
 * 总之:
 * 读: /queue,/topic
 * 写: /topic/marco,/app/xxx
 *
 * 代理:
 * /app/xxx => MessageMapping => 返回值 => /topic/marco
 * /queue,/topic => 订阅者. (服务端)
 *
 *
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/marcopolo").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //registry.enableStompBrokerRelay("/queue", "/topic")
        //.setRelayHost("localhost")
        //.setRelayPort(61313)
        //.setClientLogin("guest")
        //.setClientPasscode("guest")
        ;//使用标准的代理 (BrokerRelay表示代理中继)
        registry.enableSimpleBroker("/queue", "/topic"); // 使用内存代理
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Bean()
    public ThreadPoolTaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }



}
