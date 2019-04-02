package com.zpwan.ssoserver.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.InternalException;
import com.zpwan.appcommon.exception.DefinedError;
import com.zpwan.appcommon.exception.ServerDefinedException;
import com.zpwan.appcommon.utils.JsonUtils;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpStatus;

/**
 * @program: rocket
 * @description:
 * @author: hzzp
 * @create: 2019-03-28 17:43
 **/
@Configurable
public class ExceptionErrorDecoder implements ErrorDecoder {
    Logger logger = LoggerFactory.getLogger(ExceptionErrorDecoder.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {

        try {
            if (response.body() != null) {

                String targetMsg = null;
                String body = Util.toString(response.body().asReader());
                logger.error(body);
                DefinedError definedError  = this.objectMapper.readValue(body.getBytes("UTF-8"), DefinedError.class);
                ServerDefinedException businessException = new ServerDefinedException(HttpStatus.BAD_REQUEST.value(),definedError);
                return businessException;
            }
        } catch (Exception var4) {
            return new InternalException(var4.getMessage());
        }
        return new InternalException("系统异常,请联系管理员");
    }

}
