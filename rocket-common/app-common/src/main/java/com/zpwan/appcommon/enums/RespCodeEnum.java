package com.zpwan.appcommon.enums;

public enum RespCodeEnum {
    /**
     * 成功
     */
    OK(200, "Success"),

    CREATE(201, "Create"),

    DELETE(204, "Delete"),

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * 失败
     */
    ERROR(500, "Error"),
    /**
     * 账号密码错误
     */
    LOGIN_ACCOUNT_PWD_ERROR(40000, "账号或密码不正确"),
    /**
     * token失效
     */
    INVALID_TOKEN(40001, "Network Authentication Required"),

    /**
     * 未登录
     */
    NOT_LOGIN(40002, "未登录"),

    /**
     * token不合法
     */
    INVALID_REFRESH_TOKEN(40003, "非法令牌"),

    /**
     * 用户不存在或已被删除
     */
    LOGIN_USER_NOT_EXIST(40004, "用户不存在或已被删除"),


    UNAUTHORIZED_CLIENT(40005, "您无权访问 {}"),

    ACCOUNT_NOT_EXIST(40006, "资金账户不存在，请先开户")
    ;

    private int code;
    private String value;

    RespCodeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
