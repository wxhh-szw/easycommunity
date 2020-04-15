package com.wxhh.easycommunity.controller;

import com.wxhh.easycommunity.entity.User;
import com.wxhh.easycommunity.service.UserService;
import com.wxhh.easycommunity.utils.EasyCommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class LoginController implements EasyCommunityConstant {

    @Autowired
    private UserService userService;



    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
//        Map<String, Object> map = userService.register(user);
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);

        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功，注册邮件已发送，请进尽激活");
            model.addAttribute("target", "index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int result = userService.activation(userId, code);
        if (result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "恭喜，激活成功。");
            model.addAttribute("target", "/login");
        } else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "本次操作无效，该账号已经被激活。");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "对不起，激活码不正确。");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }

}



















