package com.xiaoyue.nov.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.util.ArrayUtils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by xiaoyue26 on 17/12/6.
 * 由于有自动配置,因此这里就不用了.
 */
//@Configuration
public class MailConfig {

    /*@Bean
    public MailSender mailSender(Environment env) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("mailserver.host"));
        mailSender.setUsername(env.getProperty("mailserver.username"));
        mailSender.setPassword(env.getProperty("mailserver.password"));
        mailSender.setPort(Integer.valueOf(env.getProperty("mailserver.port")));
        //mailSender.setDefaultEncoding();
        return mailSender;
    }
*/

}
