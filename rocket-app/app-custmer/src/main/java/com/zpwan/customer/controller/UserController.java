package com.zpwan.customer.controller;

import com.zpwan.appcommon.enums.ErrorCodeEnum;
import com.zpwan.appcommon.enums.RespCodeEnum;
import com.zpwan.appcommon.exception.ServerDefinedException;
import com.zpwan.appcommon.model.dto.UserDto;
import com.zpwan.appcommon.model.dto.UserRequestDto;
import com.zpwan.appcommon.result.RespModel;
import com.zpwan.customer.client.SsoControllerClient;
import com.zpwan.customer.client.UserControllerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @program: rocket
 * @description: 用户操作类
 * @author: hzzp
 * @create: 2019-03-12 10:56
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    Logger Log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserControllerClient userControllerClient;
    @Autowired
    SsoControllerClient ssoControllerClient;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/register")
    public HttpServletResponse register(@Validated UserDto userDto, HttpServletResponse httpResponse){
        userControllerClient.registerUser(userDto);
        RespModel respModel = RespModel.success(RespCodeEnum.CREATE);
        if(respModel==null){
            throw new ServerDefinedException(ErrorCodeEnum.E4001000,"获取token 异常");
        }
        return httpResponse;
    }
    @RequestMapping("/tologin")
    public String register(){
        return "loginView";
    }

    @PostMapping("/auth")
    public String loginAccount(Map<String, Object> paramMap, @Validated UserRequestDto requestDto,HttpServletResponse httpResponse){
        String mobile = requestDto.getMobile();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> stringRespModel = restTemplate.postForEntity("http://127.0.0.1:8892/sso/user/login",requestDto,UserResponse.class);
//        RespModel<String> stringRespModel = ssoControllerClient.login(requestDto);
        if(stringRespModel==null){
            throw new ServerDefinedException(ErrorCodeEnum.E4001000,"获取token 异常");
        }
        RespModel<String> userBody = stringRespModel.getBody();
        paramMap.put("token",userBody.getBody());
        Cookie cookie = new Cookie("token", userBody.getBody());
        cookie.setMaxAge(3600);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
        return findAll(paramMap);
    }

    @RequestMapping("/findAll")
    public String  findAll(Map<String, Object> paramMap){
        RespModel<List<UserDto>> listRespModel = userControllerClient.findAll();
        if(listRespModel==null){
            throw new ServerDefinedException(ErrorCodeEnum.E4001000,"");
        }
        paramMap.put("userList",listRespModel.getBody());
        return "userManager";
    }
    @RequestMapping("/logout")
    public String  findAll(HttpServletResponse httpResponse){
        httpResponse.addCookie(null);
        return "/index";
    }
    @GetMapping("/findByMobile")
    public RespModel<UserDto>  findByMobile(@RequestParam("mobile") String mobile){
        Log.debug("request mobile:{}",mobile);
        RespModel<UserDto> userBody =userControllerClient.findByMobile(mobile);
        UserDto userDto = userBody.getBody();
        return RespModel.success(userDto);
    }
    static class UserResponse extends RespModel<String>{

    }
}
