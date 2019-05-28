package com.panther.security.core;

import com.panther.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
// 增加注解，使SecurityProperties 配置类读取器生效
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {


    // security 5.0 新增了password 加密模式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
