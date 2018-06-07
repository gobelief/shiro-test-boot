package com.module1.project.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.module1.project.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("c")
public class WebController {
    private Logger logger = LoggerFactory.getLogger(WebController.class);
    @Autowired
    @Qualifier("redisTemplate")
    private StringRedisTemplate template;

    //    private Jedis jedis;
    @RequestMapping("")
    public String init() {
        logger.info("/c connect");
        return "index";
    }

    @RequestMapping("login")
    public String login(User user, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        logger.info("/login connect");
        Gson g = new Gson();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();

        if (usernamePasswordToken.getPassword() == null || "".equals(usernamePasswordToken.getPassword()))
            return "index";
        String hashAlgorithmName = "MD5";
        String credentials = String.valueOf(usernamePasswordToken.getPassword());
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, null, hashIterations);
        try {
            subject.login(usernamePasswordToken);
            logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证通过");
            if (subject.isAuthenticated()) {
                model.addAttribute("account", SecurityUtils.getSubject().getPrincipal());
                template.opsForValue().set("user", g.toJson(user));
                logger.info("session : " + session.getId());
                return "login";
            } else {
                usernamePasswordToken.clear();
                return "index";
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
        return "index";
    }

    @RequiresPermissions({"sss"})
    @RequestMapping("realm")
    @ResponseBody
    public Map getMap(HttpSession session) {
        Map map = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        Object user = subject.getPrincipal();
        map.put("user", user);
        return map;
    }

    @RequestMapping("logout")
    public String logout(UsernamePasswordToken usernamePasswordToken) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        usernamePasswordToken.clear();
        return "index";
    }
}
