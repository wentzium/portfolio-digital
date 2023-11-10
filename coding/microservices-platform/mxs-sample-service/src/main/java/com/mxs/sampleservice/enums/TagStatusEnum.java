package com.mxs.sampleservice.enums;

/**
 * 样本-可用状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum TagStatusEnum {
    /**
     * 未打印
     */
    UN_PRINTED(0, "未打印"),
    /**
     * 已打印
     */
    PRINTED(1, "已打印");

    private Integer code;
    private String desc;

    TagStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TagStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (TagStatusEnum e : TagStatusEnum.values()) {
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
