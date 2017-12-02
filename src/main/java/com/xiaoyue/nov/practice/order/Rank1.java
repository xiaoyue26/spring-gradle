package com.xiaoyue.nov.practice.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoyue26 on 17/12/1.
 */
@Component
@Order(3)// 先生成,但是后装配
public class Rank1 implements IRank{
    private String name="rank1";
    public Rank1() {
        System.out.println("rank1 initial");
    }
    @Override
    public String toString(){
        return name;
    }
}
