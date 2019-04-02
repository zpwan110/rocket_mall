package com.zpwan.appcommon.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * 说明：
 *
 * @author 周靖捷
 *         Created by 周靖捷 on 2017/10/19.
 */
@SuppressWarnings("deprecation")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DefinedError implements Serializable {
    private int code = 400;
    private String message;
    private String detail;
    private Object body;

    public DefinedError() {
    }

    private DefinedError(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.detail = builder.detail;
        this.body = builder.body;
    }

    public static Builder newError() {
        return new Builder();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getType() {
        if (body == null) {
            return "none";
        } else {
            return "object";
        }
    }

    public static final class Builder {
        private int code;
        private String message;
        private String detail;
        private Object body;

        private Builder() {
        }

        public DefinedError build() {
            return new DefinedError(this);
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }
    }
}
