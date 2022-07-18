package com.shenxian.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author: shenxian
 * @date: 2022/7/11 11:18
 */
@Getter
public class BadRequestException extends RuntimeException{

    private Integer status = HttpStatus.BAD_REQUEST.value();

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Integer status, String message) {
        super(message);
        this.status = status;
    }

}
