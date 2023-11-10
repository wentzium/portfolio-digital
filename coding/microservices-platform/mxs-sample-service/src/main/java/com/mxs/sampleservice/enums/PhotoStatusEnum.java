package com.mxs.sampleservice.enums;

/**
 * 样本-可用状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum PhotoStatusEnum {
    /**
     * 未拍照
     */
    _0(0, "未拍照"),
    /**
     * 已拍照
     */
    _1(1, "已拍照");

    private Integer code;
    private String desc;

    PhotoStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PhotoStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (PhotoStatusEnum e : PhotoStatusEnum.values()) {
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
