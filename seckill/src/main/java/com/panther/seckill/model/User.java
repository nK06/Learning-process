package com.panther.seckill.model;

import java.util.Date;

/**
 * @version 1.0
 * @ClassName User
 * @Description TODO
 * @date 2019-05-29 9:55
 */
public class User {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLogin_Date;
    private Integer loginCount;
    
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLogin_Date() {
        return lastLogin_Date;
    }

    public void setLastLogin_Date(Date lastLogin_Date) {
        this.lastLogin_Date = lastLogin_Date;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public User(Long id, String nickname, String password, String salt, String head, Date registerDate, Date lastLogin_Date, Integer loginCount) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.salt = salt;
        this.head = head;
        this.registerDate = registerDate;
        this.lastLogin_Date = lastLogin_Date;
        this.loginCount = loginCount;
    }
}
