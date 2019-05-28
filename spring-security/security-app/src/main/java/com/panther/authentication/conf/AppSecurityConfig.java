package com.panther.authentication.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName: AppSecurityConfig
 * @Description: TODO
 * @Author: makai
 * @Date: 2019-02-20 11:16
 * @Version: 1.0
 */
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/oauth/authorize").authenticated()
                .and().httpBasic();
    }
}
