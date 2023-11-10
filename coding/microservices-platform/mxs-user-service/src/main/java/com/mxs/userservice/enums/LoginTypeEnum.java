package com.mxs.userservice.enums;

/**
 * 登录类型枚举
 * Created by j.yang on 2019-07-07.
 */
public enum LoginTypeEnum {
    USERNAME(1, "用户名"),
    UNIQUE_CODE(2, "唯一标识码");

    private Integer code;
    private String desc;

    LoginTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LoginTypeEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (LoginTypeEnum e : LoginTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
