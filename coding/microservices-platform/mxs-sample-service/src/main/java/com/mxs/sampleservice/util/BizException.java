package com.mxs.sampleservice.util;

public class BizException extends RuntimeException{
    public BizException(String message) {
        super(message);
    }
}