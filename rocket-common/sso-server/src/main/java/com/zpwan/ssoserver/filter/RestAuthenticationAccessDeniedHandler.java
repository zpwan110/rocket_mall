package com.zpwan.ssoserver.filter;

import com.zpwan.appcommon.enums.RespCodeEnum;
import com.zpwan.appcommon.result.RespModel;
import com.zpwan.appcommon.utils.JsonUtils;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-19 16:44
 **/

@Component("RestAuthenticationAccessDeniedHandler")
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, org.springframework.security.access.AccessDeniedException e) throws IOException, ServletException {
        //登陆状态下，权限不足执行该方法
        System.out.println("权限不足：" + e.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = JsonUtils.DEFAULT.toJson(RespModel.build(RespCodeEnum.UNAUTHORIZED_CLIENT,e.getMessage(),""));
        printWriter.write(body);
        printWriter.flush();
    }
}
