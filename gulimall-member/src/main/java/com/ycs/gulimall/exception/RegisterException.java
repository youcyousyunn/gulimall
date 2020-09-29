package com.ycs.gulimall.exception;

import lombok.Data;

@Data
public class RegisterException extends RuntimeException {
    private int code = 500;
    private String msg;

    public RegisterException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
