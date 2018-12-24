package com.panther.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService , SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("表单登錄用戶名：" + s);
        return buildUser(s);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

        logger.info("社交登錄用戶名：" + userId);
        return buildUser(userId);
    }


    public SocialUserDetails buildUser(String userId){

        //logger.info("登錄用戶名：" + userId);
        // 根据查找到的用户信息判断用户是否被冻结
        // 这里实际开发 放的值是数据库的加密后的密码
        return new SocialUser(userId, passwordEncoder.encode("123"),
                true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}


