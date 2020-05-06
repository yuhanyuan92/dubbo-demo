package com.qianlima.application.exception;

import com.qianlima.base.exception.ServiceException;

/**
 * @author ZHangYJ
 */
public class MyApplicationException extends ServiceException {

    public MyApplicationException() {
    }

    public MyApplicationException(ApplicationExceptionCode applicationExceptionCode) {
        this(applicationExceptionCode.getCode(), applicationExceptionCode.getMessage());
    }

    public MyApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyApplicationException(String message) {
        super(message);
    }

    public MyApplicationException(Throwable cause) {
        super(cause);
    }

    public MyApplicationException(int code) {
        super(code);
    }

    public MyApplicationException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public MyApplicationException(int code, String message) {
        super(code, message);
    }

    public MyApplicationException(int code, Throwable cause) {
        super(code, cause);
    }
}
