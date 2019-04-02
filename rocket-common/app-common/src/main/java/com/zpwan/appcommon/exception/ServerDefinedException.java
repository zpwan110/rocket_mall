package com.zpwan.appcommon.exception;

import com.zpwan.appcommon.config.BusinessMessageConfigUtil;
import com.zpwan.appcommon.enums.ErrorCodeEnum;
import com.zpwan.appcommon.enums.HttpStatusEnum;
import com.zpwan.appcommon.utils.StringUtils;
import org.springframework.http.ResponseEntity;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/10/19.
 */
public class ServerDefinedException extends RuntimeException {

    protected int httpStatus;

    protected DefinedError definedError;

    /**
     * 指定异常编码 抛出异常
     *
     * @param errorCode 异常编码
     */
    public ServerDefinedException(ErrorCodeEnum errorCode) {
        this(errorCode, "");
    }

    /**
     * 指定异常编码 抛出异常(指定格式化参数)
     *
     * @param errorCode 异常编码
     * @param format    消息格式化
     */
    public ServerDefinedException(ErrorCodeEnum errorCode, String... format) {
        this(errorCode, HttpStatusEnum.BAD_REQUEST, null, format);
    }

    /**
     * 指定异常编码、状态码 抛出异常
     *
     * @param errorCode  异常编码
     * @param httpStatus 状态码
     */
    public ServerDefinedException(ErrorCodeEnum errorCode, HttpStatusEnum httpStatus) {
        this(errorCode, httpStatus, "");
    }

    /**
     * 指定异常编码、状态码 抛出异常(指定格式化参数)
     *
     * @param errorCode  异常编码
     * @param httpStatus 状态码
     * @param format     消息格式化
     */
    public ServerDefinedException(ErrorCodeEnum errorCode, HttpStatusEnum httpStatus, String... format) {
        super(StringUtils.format(BusinessMessageConfigUtil.getMessage(errorCode.getErrorCode()), format));
        buildDefinedError(errorCode, httpStatus, format);
    }

    /**
     * 指定异常编码、异常原因 抛出异常
     *
     * @param errorCode 异常编码
     * @param cause     异常原因
     */
    public ServerDefinedException(ErrorCodeEnum errorCode, Throwable cause) {
        this(errorCode, cause, null);
    }

    /**
     * 指定异常编码、异常原因 抛出异常(指定格式化参数)
     *
     * @param errorCode 异常编码
     * @param cause     异常原因
     * @param format    消息格式化
     */
    public ServerDefinedException(ErrorCodeEnum errorCode, Throwable cause, String... format) {
        this(errorCode, HttpStatusEnum.BAD_REQUEST, cause, format);
    }


    /**
     * 指定异常编码、状态码、异常原因 抛出异常
     *
     * @param errorCode  异常编码
     * @param httpStatus 状态码
     * @param cause      异常原因
     */
    public ServerDefinedException(ErrorCodeEnum errorCode, HttpStatusEnum httpStatus, Throwable cause) {
        this(errorCode, httpStatus, cause, "");
    }

    public ServerDefinedException(int status, DefinedError error) {
        this.httpStatus = status;
        this.definedError = error;
    }

    /**
     * 指定异常编码、状态码、异常原因 抛出异常(指定格式化参数)
     *
     * @param errorCode  异常编码
     * @param httpStatus 状态码
     * @param cause      异常原因
     * @param format     异常内容格式化
     */
    private ServerDefinedException(ErrorCodeEnum errorCode, HttpStatusEnum httpStatus, Throwable cause, String... format) {
        super(StringUtils.format(BusinessMessageConfigUtil.getMessage(errorCode.getErrorCode()), format), cause);
        buildDefinedError(errorCode, httpStatus, format);
    }

    private void buildDefinedError(ErrorCodeEnum errorCode, HttpStatusEnum httpStatus, String... format) {
        this.httpStatus = httpStatus.value();
        DefinedError error = DefinedError.newError().build();
        error.setCode(errorCode.getErrorCode());
        error.setMessage(StringUtils.format(BusinessMessageConfigUtil.getMessage(errorCode.getErrorCode()), format));
        this.definedError = error;
    }
    public ResponseEntity<DefinedError> toResp() {
        return ResponseEntity.status(httpStatus).body(this.definedError);
    }

}
