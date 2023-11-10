package com.mxs.sampleservice.enums;

/**
 * 样本-流程状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum SampleFlowStatusEnum {
    /**
     * 待固定
     */
    _1(1, "待固定"),
    /**
     * 待转运
     */
    _2(2, "待转运"),
    /**
     * 待接收
     */
    _3(3, "待接收"),
    /**
     * 已接收
     */
    _4(4, "已接收");

    private Integer code;
    private String desc;

    SampleFlowStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SampleFlowStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (SampleFlowStatusEnum e : SampleFlowStatusEnum.values()) {
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
