package com.xiaoyue.nov;

import java.util.List;

import com.xiaoyue.nov.ctrl.ReaderHandlerMethodArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ReadingListApplication {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ReadingListApplication.class);
        // To disabled web environment, change `true` to `false`
        application.setWebEnvironment(true);
        application.run(args);
    }


}
