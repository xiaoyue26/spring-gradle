package com.xiaoyue.nov.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoyue26 on 17/12/12.
 */
@Component
public class Runner implements CommandLineRunner {
    private final IService service;

    @Autowired
    public Runner(IService service) {
        this.service = service;
    }


    @Override
    public void run(String... strings) throws Exception {
        service.serve();
    }
}
