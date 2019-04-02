package com.zpwan.customer.client;

import com.zpwan.appcommon.model.dto.UserDto;
import com.zpwan.appcommon.model.dto.UserRequestDto;
import com.zpwan.appcommon.result.RespModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Documented;
import java.util.List;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-12 11:00
 **/
@FeignClient(name = "service-user",url = "127.0.0.1:8091",path = "/service/users")
public interface UserControllerClient {
    @PostMapping("/add")
    void registerUser(@RequestBody UserDto userDTO);

    @GetMapping("/findByMobile")
    RespModel<UserDto> findByMobile(@RequestParam("mobile") String mobile);

    @GetMapping("/findAll")
    RespModel<List<UserDto>> findAll();
}
