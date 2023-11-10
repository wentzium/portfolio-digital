package com.mxs.sampleservice.enums;

/**
 * 样本-可用状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum LogisticsStatusEnum {
    /**
     * 未送出
     */
    _0(0, "未送出"),
    /**
     * 运送中
     */
    SHIPPING(1, "运送中"),
    /**
     * 已签收
     */
    ACCEPT(2, "已签收"),
    /**
     * 拒收
     */
    REJECT(3, "拒收");

    private Integer code;
    private String desc;

    LogisticsStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LogisticsStatusEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (LogisticsStatusEnum e : LogisticsStatusEnum.values()) {
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
