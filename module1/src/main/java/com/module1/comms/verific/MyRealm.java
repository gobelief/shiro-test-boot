package com.module1.comms.verific;

import com.module1.project.pojo.User;
import com.module1.project.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
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
        List<User> list = userService.byName(username);
        if (list.size() != 1){
            return null;
        }
        User user = list.get(0);
        if (user != null) {
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());
        }
        return null;
    }
}
