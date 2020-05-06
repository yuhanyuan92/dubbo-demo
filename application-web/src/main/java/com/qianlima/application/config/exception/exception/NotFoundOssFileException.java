package com.qianlima.application.config.exception.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundOssFileException extends RuntimeException{
    private String message;
    public NotFoundOssFileException(String message){
        super(message);
        this.message=message;
    }
}
