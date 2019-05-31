package com.panther.seckill.vo;

import com.panther.seckill.validate.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @version 1.0
 * @ClassName LoginVo
 * @Description TODO
 * @date 2019-05-31 10:28
 */
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    
    @NotNull
    @Length(min = 32)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
