package com.panther.security.core;

import com.panther.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
// 增加注解，使SecurityProperties 配置类读取器生效
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
