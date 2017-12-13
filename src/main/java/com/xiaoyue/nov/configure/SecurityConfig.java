package com.xiaoyue.nov.configure;

import com.xiaoyue.nov.storage.jpa.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.xiaoyue.nov.storage")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ReaderRepository readerRepository;

    @Override // 配置Spring Security的Filter链
    public void configure(WebSecurity webSecurity) throws Exception {

    }

    @Override// 配置如何通过拦截器保护请求
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/*").access(
                "hasRole('ACTUATOR') and hasRole('READER')")
                .antMatchers("/").hasRole("READER")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60)
                .key("rememberkey");
                /*.and().requiresChannel()
                .antMatchers("/login")
                .requiresSecure()*/
        ;
    }

    @Override// 配置user-detail服务
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                /*.and()
                .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN", "READER")*/
        ;

        // use jdbc:
        /*auth.jdbcAuthentication().authoritiesByUsernameQuery(
                "select * from xxx"
        ).passwordEncoder(new StandardPasswordEncoder("53cr3t"));*/
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException {
                UserDetails userDetails = readerRepository.findOne(username);
                if (userDetails != null) {
                    return userDetails;
                }
                throw new UsernameNotFoundException("User '" + username + "' not found.");
            }
        };
    }


}
