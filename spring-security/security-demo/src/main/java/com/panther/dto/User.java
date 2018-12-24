package com.panther.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.panther.validator.MyConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

public class User {

    // JsonView 限制字段展示视图

    public interface UserSimpleView{};
    public interface UserDetailView extends UserSimpleView {};

    private String id;

    @MyConstraint(message = "这是测试校验")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;


    @Past(message = "生日必须是过去的时间") // 过去的时间
    private Date birthDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    @JsonView(UserDetailView.class)
    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
