package com.module1.project.web;

import com.module1.project.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("c")
public class WebController {
    private Logger logger = LoggerFactory.getLogger(WebController.class);

    @RequestMapping("")
    public String init() {
        logger.info("/c connect");
        return "index";
    }

    @RequestMapping("login")
    public String login(User user, RedirectAttributes redirectAttributes, Model model) {
        logger.info("/login connect");
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        String hashAlgorithmName = "MD5";
        String credentials = String.valueOf(usernamePasswordToken.getPassword());
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, null, hashIterations);

        try {
            subject.login(usernamePasswordToken);
            logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证通过");
            if(subject.isAuthenticated()){
                return "login";
            }else {
                usernamePasswordToken.clear();
                return "forward:/c";
            }
        } catch (IncorrectCredentialsException ice) {
            ice.printStackTrace();
            logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "s密码不正确");
            model.addAttribute("err","密码不正确");
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err","密码不正确");
            redirectAttributes.addFlashAttribute("message", "s密码不正确");
            logger.info("对用户[" + user.getUsername() + "]进行登录验证..验证未通过,错误的凭证");
        }
        usernamePasswordToken.clear();
        return "forward:/c";
    }
    @RequiresPermissions({"sss"})
    @RequestMapping("realm")
    @ResponseBody
    public Map getMap() {
        Map map = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        Object user = subject.getPrincipal();
        map.put("user",user);
        return map;
    }
}
