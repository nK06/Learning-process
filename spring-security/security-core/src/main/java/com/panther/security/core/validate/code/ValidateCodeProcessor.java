package com.panther.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/***
 * 校验码处理器，封装了不同的校验码处理逻辑
 */
public interface ValidateCodeProcessor {

    /***
     * 验证码放入Session的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /***
     * 创建验证码
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);
}
