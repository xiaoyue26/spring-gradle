package com.xiaoyue.nov.cd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by xiaoyue26 on 17/12/1.
 */
@Configuration
@EnableAspectJAutoProxy
public class TrackCounterConfig {
    @Bean
    public CompactDisc compactDisc(){
        return new CompactDisc();
    }


    @Bean
    public TrackCounter trackCounter() {
        return new TrackCounter();
    }

}
