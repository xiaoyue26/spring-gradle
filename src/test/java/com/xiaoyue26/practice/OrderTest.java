package com.xiaoyue26.practice;

import com.xiaoyue.nov.ReadingListApplication;
import com.xiaoyue.nov.practice.order.IRank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by xiaoyue26 on 17/12/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadingListApplication.class)
@WebAppConfiguration
public class OrderTest {
    @Autowired
    List<IRank> ranks;

    @Test
    public void testListOrder() {
        for (IRank rank : ranks) {
            System.out.println(rank);// rank1先初始化,但是后装配
        }
    }

}
