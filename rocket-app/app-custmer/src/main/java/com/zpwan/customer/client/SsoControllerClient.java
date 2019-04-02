package com.zpwan.customer.client;

import com.zpwan.appcommon.model.dto.UserRequestDto;
import com.zpwan.appcommon.result.RespModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-12 11:01
 **/
@FeignClient(name = "sso-server",url = "127.0.0.1:8892",path = "/sso/user")
public interface SsoControllerClient {
    @PostMapping("/login")
    RespModel login(@RequestBody UserRequestDto userRequest);
}
