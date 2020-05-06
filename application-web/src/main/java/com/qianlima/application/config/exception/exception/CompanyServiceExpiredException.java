package com.qianlima.application.config.exception.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyServiceExpiredException extends RuntimeException {
    private String message;
    public CompanyServiceExpiredException(String message){
        super(message);
        this.message=message;
    }
}
