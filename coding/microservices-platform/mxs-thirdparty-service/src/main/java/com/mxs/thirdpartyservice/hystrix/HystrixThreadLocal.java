package com.mxs.thirdpartyservice.hystrix;

public class HystrixThreadLocal {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
}