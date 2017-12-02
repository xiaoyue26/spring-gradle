package com.xiaoyue.nov.practice.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoyue26 on 17/12/1.
 */
@Component
@Order(2)
public class Rank2 implements IRank{
    private String name="rank2";
    public Rank2(){
        System.out.println("rank2 initial");
    }
    @Override
    public String toString(){
        return name;
    }
}
