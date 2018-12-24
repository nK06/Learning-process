package com.panther.security.browser;

import com.panther.security.core.authentication.AbstractChannelSecurityConfig;
import com.panther.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.panther.security.core.properties.SecurityConstants;
import com.panther.security.core.properties.SecurityProperties;
import com.panther.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration                              // security web端适配器类
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;


    // security 5.0 新增了password 加密模式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /***
     * SpringSecurity 的rememberme Token 需存放到数据库
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){

        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 在启动的时候会自动创建表
        //tokenRepository.setCreateTableOnStartup(false);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //// 初始化ValidateCodeFilter图片验证码过滤器
        //ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        //validateCodeFilter.setAuthenticationFailureHandler(pantherAuthenticationFailureHandler);
        //validateCodeFilter.setSecurityProperties(securityProperties);
        //validateCodeFilter.afterPropertiesSet();
        //
        //// 初始化ValidateCodeFilter短信验证码过滤器
        //SmsCodeFilter smsFilter = new SmsCodeFilter();
        //smsFilter.setAuthenticationFailureHandler(pantherAuthenticationFailureHandler);
        //smsFilter.setSecurityProperties(securityProperties);
        //smsFilter.afterPropertiesSet();

        applyPasswordAuthenticationConfig(http);
        //http.httpBasic()
        //自定义过滤器需要添加到UsernamePasswordAuthenticationFilter 之前。将过滤器对象传进去
        http.apply(validateCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            // 记住我功能
            .rememberMe()
                // 设置对应的参数，如数据源，过期时间，userDetailsService用户登录services
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
            .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*")
                .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .csrf().disable()
                ;
    }

}


















