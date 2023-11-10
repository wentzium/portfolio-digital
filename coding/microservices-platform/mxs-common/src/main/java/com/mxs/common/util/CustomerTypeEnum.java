package com.mxs.common.util;

/**
 * 登录类型枚举
 * Created by j.yang on 2019-06-17.
 */
public enum CustomerTypeEnum {
    CLINICIAN(1, "临床医生","101001"),
    PATHOLOGIST(2, "病理科医生","101002"),
    OPERATION_NURSE(3, "手术室护士","101003"),
    SECURITY_NURSE(4, "保障科护士","101004");

    private Integer code;
    private String desc;
    private String itemCode;

    CustomerTypeEnum(Integer code, String desc,String itemCode) {
        this.code = code;
        this.desc = desc;
        this.itemCode = itemCode;
    }

    public static CustomerTypeEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (CustomerTypeEnum e : CustomerTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return CLINICIAN;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getItemCode() {
        return itemCode;
    }
}
