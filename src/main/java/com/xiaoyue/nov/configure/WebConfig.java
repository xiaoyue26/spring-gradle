package com.xiaoyue.nov.configure;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by xiaoyue26 on 17/12/8.
 */
public class WebConfig extends WebMvcConfigurerAdapter {
    public void test1() {
        System.out.println("here");
    }

    public static void main(String[] args) {
        WebConfig obj = new WebConfig();
        obj.test1();
        System.out.println("there");
    }
}
