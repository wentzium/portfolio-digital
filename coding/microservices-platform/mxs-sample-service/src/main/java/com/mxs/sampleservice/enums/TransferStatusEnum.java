package com.mxs.sampleservice.enums;

/**
 * 样本-转运状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum TransferStatusEnum {
    /**
     * 正常
     */
    _0(0, "正常"),
    /**
     * 已转箱
     */
    _1(1, "已转箱");

    private Integer code;
    private String desc;

    TransferStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TransferStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (TransferStatusEnum e : TransferStatusEnum.values()) {
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
