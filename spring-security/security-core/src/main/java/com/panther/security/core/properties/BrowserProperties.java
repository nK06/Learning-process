package com.panther.security.core.properties;

/***
 * 浏览器端的配置类。
 */
public class BrowserProperties {

    /***
     * 默认登录页面
     */
    private String loginPage = "/singin.html"; // 如果用户没有指定登录页Url就用这个默认值

    /***
     * 登录类型配置
     */
    private LoginType loginType = LoginType.JSON;

    /***
     * 记住我功能时间限制，单位秒
     */
    private int rememberMeSeconds = 3600;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
