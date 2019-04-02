package com.zpwan.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: rocket
 * @description: 用户操作类
 * @author: hzzp
 * @create: 2019-03-12 10:56
 **/
@Controller
public class IndexController {
    @RequestMapping("/login")
    public String loginAccount(Model model){
        return "loginView";
    }


    @RequestMapping("/hello")
    public String  hello(){
        return "userManager";
    }
}
