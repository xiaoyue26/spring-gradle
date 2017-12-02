package com.xiaoyue26.practice;

import com.xiaoyue.nov.ReadingListApplication;
import com.xiaoyue.nov.practice.concert.Performance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by xiaoyue26 on 17/12/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadingListApplication.class)
@WebAppConfiguration
public class AopTest {
    @Autowired
    private Performance performance;

    @Test
    public void testAop() {
        // 可以调整@Order的大小,调整同级切面执行的顺序.
        performance.perform();

    }


}
