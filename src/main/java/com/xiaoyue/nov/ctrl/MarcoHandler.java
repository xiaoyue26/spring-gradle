package com.xiaoyue.nov.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * Created by xiaoyue26 on 17/12/8.
 *
 * 客户端js逻辑:
 * 1. 打开socketjs,发送一个消息到服务端;
 * 2. 如果收到服务端的消息,打印出来,等待2秒后,发送一个消息到服务端.
 *
 * 服务端逻辑:
 * 1. 如果接收到消息,打印出来,等到2秒后,发送一个消息到客户端.
 *
 * 因此两者形成无限循环,直到一者退出.
 */
public class MarcoHandler  extends AbstractWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(MarcoHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received message: " + message.getPayload());
        Thread.sleep(2000);
        session.sendMessage(new TextMessage("Polo!"));
    }
}
