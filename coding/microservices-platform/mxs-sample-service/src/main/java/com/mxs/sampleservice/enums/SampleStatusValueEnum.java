package com.mxs.sampleservice.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 样本状态枚举
 *
 * Created by j.yang on 2019-07-10
 */

public enum SampleStatusValueEnum {
    /**
     * 离体
     */
    NEW("NEW", "新建"),
    SEND("SEND", "发送"),
    CANCEL("CANCEL", "作废"),
    FIXED("FIXED", "固定"),
    RETURN_FROM_FIX("RETURN_FROM_FIX", "固定站点退样"),
    TRANSFER("TRANSFER", "转运"),
    MULTI_TRANSFER("TRANSFER", "多级转运"),
    RETURN_FROM_TRANSFER("RETURN_FROM_FIX", "转运站点退样"),
    ACCEPT("ACCEPT", "接收"),
    REJECTION("REJECTION", "拒收"),
    SAMPLE_TAG_PRINTED("SAMPLE_TAG_PRINTED", "样本标签打印");

    private String code;
    private String desc;

    SampleStatusValueEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SampleStatusValueEnum findByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (SampleStatusValueEnum e : SampleStatusValueEnum.values()) {
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
