package com.panther.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

public class QQimpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    /**
     * 字符串内容转对象的工具类
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    public QQimpl(String accessToken, String appId){
       super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

       this.appId = appId;

       String url = String.format(URL_GET_OPENID, accessToken);
       String result = getRestTemplate().getForObject(url,String.class);

       System.out.println(result);
       this.openId = StringUtils.substringBetween(result,"\"openId\":","}");

    }

    @Override
    public QQUserInfo getUserInfo(){
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        
        System.out.println(result);

        QQUserInfo qqUserInfo = null;
        try {
            qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败");
        }
    }
}
