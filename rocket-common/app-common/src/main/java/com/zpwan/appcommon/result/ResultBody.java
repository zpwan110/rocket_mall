package com.zpwan.appcommon.result;

/**
 * @program: spring-learn
 * @description: 返回的数据体
 * @author: hzzp
 * @create: 2019-02-28 11:18
 **/
public class ResultBody {
    /**
     * 返回码
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应结果
     */
    private Object result;

    public ResultBody(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
