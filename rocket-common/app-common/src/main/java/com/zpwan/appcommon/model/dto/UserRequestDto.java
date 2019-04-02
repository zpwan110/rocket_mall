package com.zpwan.appcommon.model.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-11 16:37
 **/
public class UserRequestDto implements Serializable {
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
