package com.mxs.sampleservice.enums;

/**
 * 样本-可用状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum FixedStatusEnum {
    /**
     * 未固定
     */
    _0(0, "未固定"),
    /**
     * 已固定
     */
    _1(1, "已固定");

    private Integer code;
    private String desc;

    FixedStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FixedStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (FixedStatusEnum e : FixedStatusEnum.values()) {
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
