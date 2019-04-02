package com.zpwan.ssoserver.config;

import com.zpwan.ssoserver.Exception.ExceptionErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-28 18:13
 **/
public class FeignConfiguration {
    public ExceptionErrorDecoder errorDecoder(){
        return new ExceptionErrorDecoder();
    }
}