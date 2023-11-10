package com.mxs.userservice.bo;

public class BizException extends RuntimeException{
    public BizException(String message) {
        super(message);
    }
}