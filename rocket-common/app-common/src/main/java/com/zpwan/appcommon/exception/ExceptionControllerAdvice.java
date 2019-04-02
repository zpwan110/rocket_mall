package com.zpwan.appcommon.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Maps;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明：通用异常处理器
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/10/19.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final String MESSAGE = "message";

    private static final String TYPE = "type";

    private static final String CODE = "code";

    private static final String DETAIL = "detail";

    private static final String BODY = "body";

    private static final String ARGS_ERROR_MESSAGE = "参数存在异常";

    private static final Pattern PATTERN = Pattern.compile("\\{.*}");
    private static final String OBJECT = "object";
    private static final String INTERNAL_ERROR = "网络异常，请稍后重试";

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
    public ExceptionControllerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity onException(RuntimeException e) throws IOException {
        Map<String, Object> resultBody = Maps.newHashMap();
        if (e.getCause() instanceof FeignException) {
            FeignException feignException = ((FeignException) e.getCause());
            if (feignException.status() != HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                Matcher matcher = PATTERN.matcher(feignException.getMessage());
                if (matcher.find()) {
                    String body = matcher.group(0);
                    if (StringUtils.isNotEmpty(body)) {
                        resultBody = objectMapper
                                .readerFor(new TypeReference<Map<String, Object>>() {
                                })
                                .readValue(body);
                        return ResponseEntity.status(feignException.status()).body(resultBody);
                    } else {
                        return ResponseEntity.status(feignException.status()).body(resultBody);
                    }
                }
            }
        }

        logger.error(INTERNAL_ERROR, e);

        if (e instanceof NestedRuntimeException) {
            if (e.getCause() != null && e.getCause() instanceof ServerDefinedException) {
                return onServerDefinedException((ServerDefinedException) e.getCause());
            }
            logger.warn("Detected Spring Nest Runtime exception", e);
            throw e;
        }
        if (e.getCause() instanceof ServerDefinedException) {
            return onServerDefinedException((ServerDefinedException) e.getCause());
        }
        resultBody.put(CODE, 500);
        resultBody.put(TYPE, "none");
        resultBody.put(MESSAGE, INTERNAL_ERROR);
        resultBody.put(DETAIL, e.getMessage());
        return ResponseEntity.status(500).body(resultBody);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(CODE, 400);
        map.put(TYPE, OBJECT);
        BindingResult result = e.getBindingResult();
        Map<String, String> message = Maps.newHashMap();
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            message.put(error.getField(), error.getDefaultMessage());
        }
        map.put(MESSAGE, ARGS_ERROR_MESSAGE);
        map.put(BODY, message);
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> onBadMethodArgumentTypeMismatchException(ConstraintViolationException e) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(CODE, 400);
        map.put(TYPE, OBJECT);
        Map<String, String> message = Maps.newHashMap();
        e.getConstraintViolations().forEach(violation -> message.put(violation.getPropertyPath().toString().substring(violation.getPropertyPath().toString().lastIndexOf('.') + 1), violation.getMessage()));
        map.put(MESSAGE, ARGS_ERROR_MESSAGE);
        map.put(BODY, message);
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    public ResponseEntity<Map<String, Object>> onHttpMessageNotReadableException(InvalidFormatException e) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(CODE, 400);
        map.put(TYPE, OBJECT);
        map.put(MESSAGE, "传入参数类型不正确");
        Map<String, String> message = Maps.newHashMap();
        List<JsonMappingException.Reference> references = e.getPath();
        for (JsonMappingException.Reference reference : references) {
            if (StringUtils.isNotBlank(reference.getFieldName())) {
                message.put(reference.getFieldName(), e.getTargetType().getName());
            }
        }
        map.put(BODY, message);
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, Object>> onHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(CODE, 400);
        map.put(TYPE, OBJECT);
        map.put(MESSAGE, "传入参数类型不正确");
        Map<String, String> message = Maps.newHashMap();
        List<JsonMappingException.Reference> references = ((InvalidFormatException)e.getCause()).getPath();
        for (JsonMappingException.Reference reference : references) {
            if (StringUtils.isNotBlank(reference.getFieldName())) {
                message.put(reference.getFieldName(), ((InvalidFormatException)e.getCause()).getTargetType().getName());
            }
        }
        map.put(BODY, message);
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(value = ServerDefinedException.class)
    @Order(100)
    @ResponseBody
    public ResponseEntity onServerDefinedException(ServerDefinedException e) {
        return e.toResp();
    }

    @ExceptionHandler(value = HystrixRuntimeException.class)
    @ResponseBody
    public ResponseEntity onHystrixException(HystrixRuntimeException e) throws IOException {
        Map<String, Object> resultBody = Maps.newHashMap();
        if (e.getCause() instanceof FeignException) {
            FeignException feignException = ((FeignException) e.getCause());
            if (feignException.status() != HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                Matcher matcher = PATTERN.matcher(feignException.getMessage());
                if (matcher.find()) {
                    String body = matcher.group(0);
                    if (StringUtils.isNotEmpty(body)) {
                        resultBody = objectMapper
                                .readerFor(new TypeReference<Map<String, Object>>() {
                                })
                                .readValue(body);
                    }
                    return ResponseEntity.status(feignException.status()).body(resultBody);
                }
            }
        }

        logger.error(INTERNAL_ERROR, e);

        if (e.getCause() instanceof ServerDefinedException) {
            return onServerDefinedException((ServerDefinedException) e.getCause());
        }
        throw e;
    }

    @ExceptionHandler(value = Error.class)
    public ResponseEntity onError(Error e) {
        logger.warn("Detected Spring Nest Runtime exception", e);

        Map<String, Object> resultBody = Maps.newHashMap();
        resultBody.put(CODE, 500);
        resultBody.put(TYPE, "none");
        resultBody.put(MESSAGE, INTERNAL_ERROR);
        resultBody.put(DETAIL, e.getMessage());
        return ResponseEntity.status(500).body(resultBody);
    }
}
