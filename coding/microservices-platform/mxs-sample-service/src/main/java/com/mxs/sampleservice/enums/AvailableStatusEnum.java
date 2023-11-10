package com.mxs.sampleservice.enums;

/**
 * 样本-可用状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum AvailableStatusEnum {
    /**
     * 可用
     */
    _0(0, "可用"),
    /**
     * 作废
     */
    _1(1, "作废");

    private Integer code;
    private String desc;

    AvailableStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AvailableStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (AvailableStatusEnum e : AvailableStatusEnum.values()) {
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
