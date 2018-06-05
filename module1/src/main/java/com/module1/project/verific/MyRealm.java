package com.module1.project.verific;

import com.module1.project.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        principalCollection.getPrimaryPrincipal();
        Subject subject = SecurityUtils.getSubject();
//        User user = (User) subject.getPrincipal();
//        user.setPermission("asdfasdfsdf");
//        user.setRole("fffffff");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("admin");
        authorizationInfo.addStringPermission("sss");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        Object o = SecurityUtils.getSubject().getPrincipal();
        String username = usernamePasswordToken.getUsername();
        String host = usernamePasswordToken.getHost();
        User user = new User();
        user.setUsername("root");
//        user.setPassword("root");// fc1709d0a95a6be30bc5926fdb7f22f4
        user.setPassword("bf006e276607f226f96120354349d81c");
        if (username != null) {
//            ByteSource.Util.bytes();
//            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(SecurityUtils.getSubject().getPrincipal(),
//                    user.getPassword(), credentialsSalt, realmName);
//            SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(user.getUsername(),ByteSource.Util.bytes(user.getPassword()),getName());
            SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());
            return a;
        }
        return null;
    }
}
