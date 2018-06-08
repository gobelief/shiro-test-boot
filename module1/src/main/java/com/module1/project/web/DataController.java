package com.module1.project.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.module1.comms.R;
import com.module1.project.pojo.User;
import com.module1.project.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("g")
public class DataController {
    private Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired
    private UserService userService;
    @RequestMapping("userAll")
    public String getUserAll(User user) {
        user.setPage(1);user.setRows(5);
        List<User> list = userService.getAll(user);
        String s = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().create().toJson(user);
        return new Gson().toJson(list);
    }
    @RequestMapping("login")
    public String login(User user, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        R r = new R();
        logger.info("/login connect");
        Gson g = new Gson();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();

        if (usernamePasswordToken.getPassword() == null || "".equals(usernamePasswordToken.getPassword()))
            return g.toJson(r.putErr("null","密码为空").getMap());
        String hashAlgorithmName = "MD5";
        String credentials = String.valueOf(usernamePasswordToken.getPassword());
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, null, hashIterations);
        try {
            subject.login(usernamePasswordToken);
            logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证通过");
            if (subject.isAuthenticated()) {
                model.addAttribute("account", SecurityUtils.getSubject().getPrincipal());
                logger.info("session : " + session.getId());
                return g.toJson(r.putOk("user","ok").getMap());
            } else {
                usernamePasswordToken.clear();
                return g.toJson(r.putErr("null","密码为空").getMap());
            }
        } catch (UnknownAccountException uae) {
            //username wasn't in the system, show them an error message?
            model.addAttribute("err", "账户不存在！");
        } catch (IncorrectCredentialsException ice) {
            //password didn't match, try again?
            usernamePasswordToken.clear();
            logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "s密码不正确");
            model.addAttribute("err", "密码不正确！");
        } catch (LockedAccountException lae) {
            //account for that username is locked - can't login.  Show them a message?
            model.addAttribute("err","账户已锁定！");
        } catch (Exception e) {
            e.printStackTrace();
            usernamePasswordToken.clear();
            logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证未通过");
        }
        usernamePasswordToken.clear();
        return g.toJson(r.putErr("null","密码或账号错误!").getMap());
    }
    @RequestMapping("logout")
    public String logout(UsernamePasswordToken usernamePasswordToken) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        usernamePasswordToken.clear();
        return new Gson().toJson(new R().putOk("null","ok").getMap());
    }
}
