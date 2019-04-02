package com.zpwan.appcommon.exception;

/**
 * Json解析错误
 *
 * @author wuaj
 * @version 1.0.0.0
 * @create 2016-05-30
 */
public class JsonException extends RuntimeException {
    public JsonException() {
        super();
    }

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }
}