package com.zpwan.appcommon.model.dto;


import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-11 17:13
 **/
public class UserDto implements Serializable {
    private Short id;

    private String nickName;

    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 18,message = "密码不能小于6位且不能大于8位")
    private String password;

    private String gender;

    @NotBlank(message = "手机号不能为空")
    @Length(min=11,max=11,message = "手机号格式不对")
    private String mobile;

    private String email;

    private String content;

    private Date createTime;

    private Date updateTime;

    private String headUrl;


    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

}
