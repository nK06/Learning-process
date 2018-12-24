package com.panther.security.core.validate.code;

import com.panther.security.core.properties.SecurityProperties;
import com.panther.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/***
 * 短信验证码校验器
 * 继承了spring工具类 保证过滤器只会调用一次
 */
                                    // 实现这个接口的目的是在其他参数加载完毕时会初始化 urls 的值
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    // 默认失败处理，会把错误信息返回前端
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // 配置文件中的需要图片验证码校验的 url 集合
    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;
    // URL 匹配工具类
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    // 实现接口中的方法
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 将配置文件中的 urls 内容 读取出来存到数组中
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(),",");
        for(String configUrl: configUrls){
            urls.add(configUrl);
        }
        // 登录请求默认要加
        urls.add("/authentication/mobile");
    }

    /***
     * 验证码校验逻辑，filter默认的方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 遍历 urls 如果有匹配到的就需要做图片验证码校验
        boolean action = false;
        for(String url: urls) {
            if(pathMatcher.match(url,httpServletRequest.getRequestURI())) {
                action = true;
            }
        }

        if(action) {
            try{
                // spring 的 session工具。需要用ServletWebRequest 包装
                validate(new ServletWebRequest(httpServletRequest));
            }catch (ValidateCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }

        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    /***
     * 验证码校验方法
     * @param request
     */
    private void validate(ServletWebRequest request) {

        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
