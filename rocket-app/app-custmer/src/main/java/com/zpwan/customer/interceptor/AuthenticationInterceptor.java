package com.zpwan.customer.interceptor;

import com.zpwan.customer.anotation.PassToken;
import com.zpwan.customer.anotation.ValidToken;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: rocket
 * @description: 验证token 拦截器
 * @author: hzzp
 * @create: 2019-03-11 13:45
 **/
public class AuthenticationInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从request中获取请求头中的token
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }

        }
        //检查有没有需要用户权限的注解
        if(method.isAnnotationPresent(ValidToken.class)){
            ValidToken validToken = method.getAnnotation(ValidToken.class);
//                if (validToken.required()) {
//                    // 执行认证
//                    if (token == null) {
//                        throw new RuntimeException("无token，请重新登录");
//                    }
//                    // 获取 token 中的 user id
//                    String userId;
//                    try {
//                        userId = JWTUtils.phaseToken(token).get("userId").toString();
//                        User user = userService.findUserById(userId);
//                        if (user == null) {
//                            throw new RuntimeException("用户不存在，请重新登录");
//                        }
//                        // 验证 token
//                        JWTUtils.
//                        JWTVerifier jwtVerifier = J.require(SignatureAlgorithm.HMAC256(user.getPassword())).build();
//                        jwtVerifier.verify(token);
//                    } catch (ExpiredJwtException e) {
//                       e.printStackTrace();
//                    }catch (SignatureException es){
//                        es.printStackTrace();
//                    }catch (exception ex){
//                        ex.printStackTrace();
//                    }
//                    return true;
//                }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
