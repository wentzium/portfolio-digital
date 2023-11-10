package com.mxs.userservice.enums;

/**
 * 登录用户状态枚举
 * Created by j.yang on 2019-07-07
 */
public enum LoginUserStatusEnum {
    NORMAL(1, "正常"),
    LOCKED(2, "锁定");

    private Integer code;
    private String desc;

    LoginUserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LoginUserStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (LoginUserStatusEnum e : LoginUserStatusEnum.values()) {
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
