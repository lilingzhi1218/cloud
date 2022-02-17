package com.example.llz.rulengine.utils.exception.base;

import lombok.Getter;

/**
 * 基础异常类，所有自定义异常类都需要继承本类
 * @author caihua
 * @date 2019/7/27
 */
@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 4691347798891863465L;

    /** 异常返回结果 */
    protected ErrorType errorType;
    /** 异常消息参数 */
    protected Object[] args;

    public BaseException(String message) {
        super(message);
        this.errorType = new ErrorType() {
            @Override
            public String getCode() {
                return ErrorType.EC_SERVICE_EXCEPTION;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

    public BaseException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public BaseException(String code, String message) {
        super(message);
        this.errorType = new ErrorType() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.errorType = new ErrorType() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
    }

    public BaseException(ErrorType errorType, Object[] args, String message) {
        super(message);
        this.errorType = errorType;
        this.args = args;
    }

    public BaseException(ErrorType errorType, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
        this.args = args;
    }
}
