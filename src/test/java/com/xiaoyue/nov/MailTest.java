package com.xiaoyue.nov;

import com.xiaoyue.nov.configure.MailConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by xiaoyue26 on 17/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadingListApplication.class)
@WebAppConfiguration
public class MailTest {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    @Ignore
    public void testMail() throws Exception {
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setFrom("no-reply@fenbi.com");
        msg.setTo("fengmq01@fenbi.com");
        msg.setSubject("daily report from gradle");
        msg.setText("daily report from gradle when msg is fake " +
                "and test successful~\n"+new Date());
        System.out.println(mailSender.getHost());
        System.out.println(mailSender.getJavaMailProperties());
        System.out.println(mailSender.getPassword());
        System.out.println(mailSender.getPort());
        System.out.println(mailSender.getUsername());
        System.out.println(mailSender.getProtocol());
        mailSender.send(msg);

    }
}
