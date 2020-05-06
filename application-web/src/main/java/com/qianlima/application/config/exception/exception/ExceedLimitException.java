package com.qianlima.application.config.exception.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceedLimitException extends RuntimeException {
    private String message;
    public ExceedLimitException(String message){
        super(message);
        this.message=message;
    }
}
