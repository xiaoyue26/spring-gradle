package com.xiaoyue.nov.ctrl;

import com.xiaoyue.nov.storage.pojo.Shout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by xiaoyue26 on 17/12/8.
 * 被动响应客户端消息,类似HTTP.
 */
@Controller
public class MsgController {
    private static final Logger logger = LoggerFactory.getLogger(MsgController.class);

    @MessageMapping("/marco")// 接受/app/marco消息. 同时可以回复一条信息.如果不想回复,就设成void.
    @SendTo("/topic/marco")
    public Shout handleShout(Shout incoming) {// 默认回复地址会在 /marco前面加上 /topic.
        logger.info("Received message: " + incoming.getMessage());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        Shout outgoing = new Shout();
        outgoing.setMessage("Polo!");

        return outgoing;
    }
    // 由于/topic开头的消息不会被转发到这里,因此监听无效.
    @SubscribeMapping({"/marco"}) // 监听/topic/marco的订阅,返回感谢订阅信息.
    public Shout handleSubsciption() {
        Shout outgoing = new Shout();
        outgoing.setMessage("感谢订阅");
        System.out.println("订阅事件发生");
        return outgoing;

    }


}
