package com.xiaoyue26.practice;

import com.xiaoyue.nov.ReadingListApplication;
import com.xiaoyue.nov.practice.cd.CompactDisc;
import com.xiaoyue.nov.practice.cd.TrackCounter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xiaoyue26 on 17/12/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadingListApplication.class)
public class AopWithArgsTest {
    @Autowired
    private CompactDisc compactDisc;
    @Autowired
    private TrackCounter counter;

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
