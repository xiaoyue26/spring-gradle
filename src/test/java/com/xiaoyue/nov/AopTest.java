package com.xiaoyue.nov;

import com.xiaoyue.nov.cd.CompactDisc;
import com.xiaoyue.nov.cd.TrackCounter;
import com.xiaoyue.nov.concert.Performance;
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
//@SpringApplicationConfiguration(classes = ReadingListApplication.class)
@SpringBootTest(classes = ReadingListApplication.class)
@WebAppConfiguration
public class AopTest {
    @Autowired
    private Performance performance;

    @Autowired
    private CompactDisc compactDisc;
    @Autowired
    private TrackCounter counter;

    @Test
    public void testAop() {
        performance.perform();
    }

    @Test
    public void testAopWithArgs() {
        compactDisc.playTrack(1);
        compactDisc.playTrack(2);
        compactDisc.playTrack(2);
        System.out.println(counter.getPlayCount(1));
        System.out.println(counter.getPlayCount(2));
        System.out.println(counter.getPlayCount(3));
    }
}
