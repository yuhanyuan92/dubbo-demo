package com.qianlima.application.config.exception.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterException extends RuntimeException {
    private String message;
    public ParameterException(String message){
        super(message);
        this.message=message;
    }
}
