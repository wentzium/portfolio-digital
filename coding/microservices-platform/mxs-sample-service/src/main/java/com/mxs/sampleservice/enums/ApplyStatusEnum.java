package com.mxs.sampleservice.enums;

/**
 * Created by j.yang on 2019-07-10
 */

public enum ApplyStatusEnum {
    /**
     * 未发送
     */
    _0(0, "未发送"),
    /**
     * 已发送
     */
    _1(1, "已发送");

    private Integer code;
    private String desc;

    ApplyStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ApplyStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (ApplyStatusEnum e : ApplyStatusEnum.values()) {
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
