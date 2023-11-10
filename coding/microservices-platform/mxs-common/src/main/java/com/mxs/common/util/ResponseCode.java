package com.mxs.common.util;

public enum ResponseCode {
    SUCCESS(200, "操作成功"),
    FAILED(201, "操作失败"),
    NOT_FOUND(404, "未发现资源"),
    SERVER_ERROR(500, "服务器错误");

    public int code;
    public String text;

    ResponseCode(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
