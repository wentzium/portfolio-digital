package com.mxs.common.util;

/**
 * @Author: j.yang
 * @Date: 2019-06-17
 */
public enum IsDeleteEnum {
    NO(0, "未删除"),
    YES(1, "已删除");

    public int code;
    public String desc;

    IsDeleteEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
