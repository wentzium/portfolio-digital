package com.mxs.sampleservice.enums;

/**
 * 样本-可用状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum SeparationStatusEnum {
    /**
     * 未离体
     */
    _0(0, "未离体"),
    /**
     * 已离体
     */
    _1(1, "已离体");

    private Integer code;
    private String desc;

    SeparationStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SeparationStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (SeparationStatusEnum e : SeparationStatusEnum.values()) {
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
