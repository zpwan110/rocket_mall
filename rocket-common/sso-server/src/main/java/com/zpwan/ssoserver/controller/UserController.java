package com.zpwan.ssoserver.controller;

import com.zpwan.appcommon.exception.ServerDefinedException;
import com.zpwan.appcommon.enums.ErrorCodeEnum;
import com.zpwan.appcommon.model.dto.UserDto;
import com.zpwan.appcommon.result.RespModel;
import com.zpwan.appcommon.utils.StringUtils;
import com.zpwan.ssoserver.client.UserControllerClient;
import com.zpwan.appcommon.model.dto.UserRequestDto;
import com.zpwan.ssoserver.utils.JWTUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-11 17:05
 **/
@RestController
@RequestMapping("/sso/user")
public class UserController {
    Logger Log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserControllerClient userControllerClient;


    @PostMapping("/login")
    public RespModel login(@RequestBody UserRequestDto userRequest){
        Log.info("login request:"+userRequest.getMobile());
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<UserResponse> forEntity = restTemplate.getForEntity("http://localhost:8091/service/users/findByMobile?mobile={1}",
//                UserResponse.class,userRequest.getMobile());
        RespModel<UserDto> userBody = userControllerClient.findByMobile(userRequest.getMobile());
//        if(forEntity==null){
//            throw new ServerDefinedException(ErrorCodeEnum.E4001000,"用户为空");
//        }
//        RespModel<UserDto> userBody = forEntity.getBody();
        UserDto userDto = userBody.getBody();
        if(userDto==null){
            throw new ServerDefinedException(ErrorCodeEnum.E4001000,"用户不存在");
        }
        if(!StringUtils.equals(userDto.getPassword(),userRequest.getPassword())){
            throw new ServerDefinedException(ErrorCodeEnum.E4001020,"用户密码");
        }
        Map<String,Object> payLoad = new HashMap<>();
        payLoad.put("userId",userDto.getId());
        payLoad.put("headerUrl",userDto.getHeadUrl());
        payLoad.put("mobile",userDto.getMobile());
        payLoad.put("realName",userDto.getRealName());
        //设置过期时间
        payLoad.put("exp", DateTime.now().plusMillis(1).toDate().getTime()/1000);
        return RespModel.success(JWTUtils.generatorToken(payLoad));
    }
    public static Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entitiClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entitiClass = (Class<?>) actualTypeArguments[0];
            }
        }

        return entitiClass;
    }

    @PostMapping("/findByMobile")
    public RespModel<UserDto>  findByMobile(@RequestBody UserRequestDto userRequest){
        Log.debug("request mobile:{}",userRequest.getMobile());
        RespModel<UserDto> userBody =userControllerClient.findByMobile(userRequest.getMobile());
        UserDto userDto = userBody.getBody();
        return RespModel.success(userDto);
    }

    public static void main(String[] args) {
        RespModel<UserDto> dtoRespModel= new RespModel<UserDto>();
        Class<UserDto> userDtoClass = (Class<UserDto>) getActualTypeArgument(dtoRespModel.getClass());
        System.out.println(userDtoClass.getClass());
    }

    static class UserResponse extends RespModel<UserDto>{

    }
}

