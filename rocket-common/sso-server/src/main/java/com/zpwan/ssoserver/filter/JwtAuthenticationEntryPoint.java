package com.zpwan.ssoserver.filter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zpwan.appcommon.enums.HttpStatusEnum;
import com.zpwan.appcommon.enums.RespCodeEnum;
import com.zpwan.appcommon.result.RespModel;
import com.zpwan.appcommon.utils.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-19 16:33
 **/
@Component(value="JwtAuthenticationEntryPoint")
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        System.out.println("认证失败：" + authException.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = JsonUtils.DEFAULT.toJson(RespModel.build(RespCodeEnum.UNAUTHORIZED,authException.getMessage(),""));
        printWriter.write(body);
        printWriter.flush();
    }
}
