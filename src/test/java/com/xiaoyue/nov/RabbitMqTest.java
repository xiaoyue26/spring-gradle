package com.xiaoyue.nov;

import com.xiaoyue.nov.practice.mq.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
    public void hello() throws Exception {
        sender.send();
    }

}
