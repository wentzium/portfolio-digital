package com.mxs.sampleservice.enums;

/**
 * 站点类型枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum SiteTypeEnum {
    OPERATION_SITE(1, "手术站点"),
    BOOKING_SITE(2, "预约站点"),
    OUTPATIENT_SITE(3, "门诊站点"),
    FIXED_SITE(4, "固定站点"),
    DELIVERY_SITE(5, "运送站点"),
    RECEIVE_SITE(6, "接收站点"),
    SECOND_DELIVERY_SITE(7, "运送站点(二级)"),
    BOOKING_NOTICE_SITE(8, "预约通知");

    private Integer code;
    private String desc;

    SiteTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SiteTypeEnum findByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (SiteTypeEnum e : SiteTypeEnum.values()) {
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
