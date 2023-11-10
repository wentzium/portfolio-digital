package com.mxs.sampleservice.enums;

/**
 * 退样标记枚举
 *
 * Created by j.yang on 2019-07-10
 */
public enum ReturnFlagEnum {
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 退样中
     */
    RETURN(2, "退样中");

    private int code;
    private String desc;

    ReturnFlagEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ReturnFlagEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (ReturnFlagEnum e : ReturnFlagEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
