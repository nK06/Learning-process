package com.panther.security.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ValidateCode {

    // 随机数
    private String code;
    // 失效时间
    private LocalDateTime expireTime;

    // 构建时可能传一个过期时间间隔，比如60秒
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    /***
     * 判断验证码是否过期
     * @return
     */
    public boolean isExpried(){
        return LocalDateTime.now().isAfter(expireTime);
    }
}
