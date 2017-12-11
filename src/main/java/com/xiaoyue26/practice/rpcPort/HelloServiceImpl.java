package com.xiaoyue26.practice.rpcPort;

import org.apache.thrift.TException;

/**
 * Created by xiaoyue26 on 17/12/11.
 */
public class HelloServiceImpl implements HelloService.Iface {


    @Override
    public String hello(String name) throws TException {
        System.out.println("hello:"+name);
        return name;
    }
}
