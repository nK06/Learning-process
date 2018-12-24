package com.panther.security.core.social.qq.connet;

import com.panther.security.core.social.qq.api.QQ;
import com.panther.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import java.io.IOException;

public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试请求是否可用
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 把QQ用户信息提取出来包装到 ConnectionValues 标准的数据结构上
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());

    }

    /**
     * 绑定解绑后的操作
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }


    @Override
    public void updateStatus(QQ api, String message) {
        // do nothing
    }
}
