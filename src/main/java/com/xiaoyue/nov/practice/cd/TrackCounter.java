package com.xiaoyue.nov.practice.cd;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoyue26 on 17/12/1.
 */
@Aspect
public class TrackCounter {
    private Map<Integer, Integer> trackCounts = new HashMap<>();

    @Pointcut("execution(* com.xiaoyue.nov.practice.cd.CompactDisc.playTrack(int))"
            + "&& args(trackNumber)"
    )
    public void trackPlayed(int trackNumber) {
    }

    @Before("trackPlayed(trackNumber)")
    public void countTrack(int trackNumber) {
        int currentCount = getPlayCount(trackNumber);
        trackCounts.put(trackNumber, currentCount + 1);
    }

    public int getPlayCount(Integer trackNumber) {
        return trackCounts.containsKey(trackNumber)
                ? trackCounts.get(trackNumber)
                : 0;
    }
}
