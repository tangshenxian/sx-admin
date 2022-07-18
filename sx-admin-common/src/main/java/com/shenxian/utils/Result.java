package com.shenxian.utils;

import lombok.Data;

/**
 * 统一返回处理类
 * @author: shenxian
 * @date: 2022/7/11 10:32
 */
@Data
public class Result {

    private long code;
    private String message;
    private Object data;

    private Result() {}

    public static Result success() {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    public static Result success(String message) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    public static Result success(String message, Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(500);
        result.setMessage("error");
        return result;
    }

    public static Result error(String message) {
        Result result = new Result();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static Result error(long code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Result error(long code, String message, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public Result code(long code) {
        this.code = code;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result data(Object data) {
        this.data = data;
        return this;
    }

}
