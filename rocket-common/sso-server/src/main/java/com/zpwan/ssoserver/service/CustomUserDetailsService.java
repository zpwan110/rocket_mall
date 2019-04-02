package com.zpwan.ssoserver.service;

import com.zpwan.appcommon.enums.ErrorCodeEnum;
import com.zpwan.appcommon.exception.ServerDefinedException;
import com.zpwan.appcommon.model.dto.UserDto;
import com.zpwan.appcommon.result.RespModel;
import com.zpwan.ssoserver.client.UserControllerClient;
import com.zpwan.ssoserver.model.Role;
import com.zpwan.ssoserver.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-19 17:00
 */

@Component(value="CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserControllerClient userControllerClient;

    public CustomUserDetailsService() {
    }

    @Override
    public UserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
        RespModel<UserDto> result = userControllerClient.findUserByName(name);
        if(result==null){
            throw new ServerDefinedException(ErrorCodeEnum.E4001000,"用户查找异常");
        }
        UserDto userDto = result.getBody();
        if (userDto == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", name));
        }
        Role role = new Role();
        role.setName("USER");
        UserDetail user = new UserDetail(userDto.getRealName(),userDto.getPassword(),role);
        return user;
    }
}
