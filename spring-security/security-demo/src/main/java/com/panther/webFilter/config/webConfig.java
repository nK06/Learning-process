package com.panther.webFilter.config;

import com.panther.interceptor.TimeInterceptor;
import com.panther.webFilter.TimeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class webConfig implements WebMvcConfigurer {

    //@Autowired
    //private TimeInterceptor timeInterceptor;

    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    registry.addInterceptor(timeInterceptor);
    //}

    /**
     *  注册自定义filter
     */
    //@Bean
    //public FilterRegistrationBean timerFilter() {
    //    FilterRegistrationBean registrationBean =  new FilterRegistrationBean();
    //    TimeFilter timeFilter = new TimeFilter();
    //
    //    registrationBean.setFilter(timeFilter);
    //    List<String> url = new ArrayList<>();
    //    url.add("/*");
    //    registrationBean.setUrlPatterns(url);
    //
    //    return registrationBean;
    //}

    // 配置异步处理相关内容。包括Callable 和 DerefeedResult方式以及超时处理等
    //@Override
    //public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    //
    //}
}
