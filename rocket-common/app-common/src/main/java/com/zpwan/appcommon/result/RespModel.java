package com.zpwan.appcommon.result;

import com.zpwan.appcommon.constant.RespTypeConstant;
import com.zpwan.appcommon.enums.RespCodeEnum;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 服务端返回公共Model
 *
 * @author linq
 */
public class RespModel<T> implements Serializable {

    private static final long serialVersionUID = -7340580590296769272L;

    private int code;

    private String message;

    private T body;

    private String detail;

    private String type;

    public RespModel() {
    }

    public RespModel<T> code(int code) {
        this.code = code;
        return this;
    }

    public RespModel<T> message(String message) {
        this.message = message;
        return this;
    }

    public RespModel<T> body(T body) {
        this.body = body;
        return this;
    }

    public RespModel<T> detail(String detail) {
        this.detail = detail;
        return this;
    }

    public RespModel<T> type(String type) {
        this.type = type;
        return this;
    }

    public RespModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.type = RespTypeConstant.TYPE_NONE;
    }

    public RespModel(RespCodeEnum respCodeEnum) {
        this.code = respCodeEnum.getCode();
        this.message = respCodeEnum.getValue();
        this.type = RespTypeConstant.TYPE_NONE;
    }


    public RespModel(RespCodeEnum respCodeEnum, T body, String type) {
        this.code = respCodeEnum.getCode();
        this.message = respCodeEnum.getValue();
        this.body = body;
        this.type = type;
    }

    public RespModel(RespCodeEnum respCodeEnum, String detail) {
        this.code = respCodeEnum.getCode();
        this.message = respCodeEnum.getValue();
        this.detail = detail;
        this.type = RespTypeConstant.TYPE_NONE;
    }

    public RespModel(RespCodeEnum respCodeEnum, String detail, String type) {
        this.code = respCodeEnum.getCode();
        this.message = respCodeEnum.getValue();
        this.detail = detail;
        this.type = type;
    }

    public RespModel(RespCodeEnum respCodeEnum, T body, String detail, String type) {
        this.code = respCodeEnum.getCode();
        this.message = respCodeEnum.getValue();
        this.body = body;
        this.detail = detail;
        this.type = type;
    }

    public RespModel(int code, String message, T body, String detail, String type) {
        this.code = code;
        this.message = message;
        this.body = body;
        this.detail = detail;
        this.type = type;
    }

    public static RespModel success() {
        return baseSuccess(null, RespTypeConstant.TYPE_NONE);
    }

    public static <K> RespModel<K> success(K body) {
        if (body == null) {
            return success();
        } else if (body instanceof List) {
            return baseSuccess(body, RespTypeConstant.TYPE_ARRARY);
        } else {
            return baseSuccess(body, RespTypeConstant.TYPE_OBJECT);
        }
    }


    public static <K> RespModel<K> successNumberPage(K body) {
        return baseSuccess(body, RespTypeConstant.TYPE_NUM_PAGE);
    }

    private static <K> RespModel<K> baseSuccess(K body, String type) {
        RespModel<K> model = new RespModel<>();
        model.setBody(body);
        model.setCode(200);
        model.setType(type);
        model.setMessage("");
        return model;
    }

    public static <E> RespModel<E> build(RespCodeEnum respCodeEnum) {
        RespModel<E> respModel = new RespModel<>();
        respModel.code = respCodeEnum.getCode();
        respModel.message = respCodeEnum.getValue();
        respModel.type = RespTypeConstant.TYPE_NONE;
        return respModel;
    }

    public static <E> RespModel<E> build(E body, RespCodeEnum respCodeEnum, String type) {
        RespModel<E> respModel = new RespModel<>();
        respModel.code = respCodeEnum.getCode();
        respModel.message = respCodeEnum.getValue();
        respModel.body = body;
        respModel.type = type;
        return respModel;
    }

    public static <E> RespModel<E> build(RespCodeEnum respCodeEnum, String detail, String type) {
        RespModel<E> respModel = new RespModel<>();
        respModel.code = respCodeEnum.getCode();
        respModel.message = respCodeEnum.getValue();
        respModel.detail = detail;
        respModel.type = type;
        return respModel;
    }

    public static <E> RespModel<E> build(RespCodeEnum respCodeEnum, E body, String detail, String type) {
        RespModel<E> respModel = new RespModel<>();
        respModel.code = respCodeEnum.getCode();
        respModel.message = respCodeEnum.getValue();
        respModel.body = body;
        respModel.detail = detail;
        respModel.type = type;
        return respModel;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
