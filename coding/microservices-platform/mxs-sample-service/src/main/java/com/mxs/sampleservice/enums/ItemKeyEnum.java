package com.mxs.sampleservice.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 字典值KEY枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum ItemKeyEnum {
    USER_TO_SITE_TYPE("101", "用户类型-站点类型映射关系"),
    APPLY_PRINT_TEMPLATE("102", "申请单打印模板"),
    COLLECTION_LOCATION("100", "采集部位"),
    SAMPLE_TYPE("200", "样品类型"),
    FIXATION_FLUID("300", "固定液类型"),
    REVOKE_REASON("400", "作废原因"),
    REJECT_REASON("500", "拒收原因"),
    OPERATION_TYPE("600", "手术类型"),
    DEPT("700", "科室"),
    OPERATION_NAME("800", "手术名称");

    private String code;
    private String desc;

    ItemKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ItemKeyEnum findByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (ItemKeyEnum e : ItemKeyEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
