package com.panther.security.browser;


import com.panther.security.core.properties.SecurityProperties;
import com.panther.security.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 声明一个存储了原始请求Url的对象
    private RequestCache requestCache = new HttpSessionRequestCache();

    // Spring 跳转器
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // 引入Security-core工程得SecurityProperties
    @Autowired
    private SecurityProperties securityProperties;

    /***
     * 当需要身份认证时跳转到这里
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 引发跳转的原始url ，会存储在 HttpSessionRequestCache中。
        SavedRequest savedRequest = requestCache.getRequest(request,response);

        if(savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发的跳转请求是"+targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")) {
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");

        // CSRF 开启需要另外一种登录模式
        //return "singin.html";
    }
}