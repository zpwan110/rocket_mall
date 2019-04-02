package com.zpwan.serviceuser.controller;

import com.zpwan.appcommon.enums.HttpStatusEnum;
import com.zpwan.appcommon.exception.ServerDefinedException;
import com.zpwan.appcommon.enums.ErrorCodeEnum;
import com.zpwan.appcommon.enums.RespCodeEnum;
import com.zpwan.appcommon.model.dto.UserDto;
import com.zpwan.appcommon.result.RespModel;
import com.zpwan.serviceuser.model.RmUsers;
import com.zpwan.serviceuser.service.RmUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-12 15:36
 **/
@RestController
@RequestMapping("/service/users")
public class ServiceUserController {
    @Autowired
    RmUserService rmUserService;
    @GetMapping("/findByMobile")
    public RespModel<UserDto> findByMobile(@RequestParam("mobile") String mobile){
       UserDto userDto = new UserDto();
       RmUsers rmUsers = rmUserService.findUserByMobile(mobile);
       if(rmUsers==null){
           throw new ServerDefinedException(ErrorCodeEnum.E4001004, HttpStatusEnum.BAD_REQUEST, "账号不存在");
       }
       BeanUtils.copyProperties(rmUsers,userDto);
       return RespModel.success(userDto);
    }

    @GetMapping("/findAll")
    public RespModel<List<UserDto>> findAll(){
        List<UserDto> userDtos =new ArrayList<>();
        List<RmUsers> rmUsers = rmUserService.findAll();
        rmUsers.forEach(item->{
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(item,userDto);
            userDtos.add(userDto);
        });
        return  RespModel.success(userDtos);
    }

    @PostMapping("/add")
    public RespModel insertOne(@RequestBody UserDto userDto){
        RmUsers rmUsers = new RmUsers();
        BeanUtils.copyProperties(rmUsers,userDto);
        rmUserService.insertOne(rmUsers);
        return RespModel.success(RespCodeEnum.CREATE);
    }
}
