package com.mxs.sampleservice.enums;

/**
 * Created by j.yang on 2019-07-10
 */

public enum  OperationTypeEnum {
    one(1, "手术类型1"),
    two(2, "手术类型2"),
    three(3, "手术类型3");

    private Integer code;
    private String desc;

    OperationTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OperationTypeEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (OperationTypeEnum e : OperationTypeEnum.values()) {
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
