package com.panther.security.core.validate.code;

import com.panther.security.core.properties.SecurityProperties;
import com.panther.security.core.validate.code.image.ImageCodeGenerator;
import com.panther.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.panther.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * 配置默认的图片验证码生成器Bean
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name="imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();

        codeGenerator.setSecurityProperties(securityProperties);

        return codeGenerator;
    }

    @Bean
    //@ConditionalOnMissingBean(name="smsCodeSender")
    @ConditionalOnMissingBean(SmsCodeSender.class)  //另一种写法
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
