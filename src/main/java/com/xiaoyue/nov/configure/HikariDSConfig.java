package com.xiaoyue.nov.configure;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by xiaoyue26 on 17/12/13.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class HikariDSConfig extends HikariConfig {
    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        return new HikariDataSource(this);
    }
}
