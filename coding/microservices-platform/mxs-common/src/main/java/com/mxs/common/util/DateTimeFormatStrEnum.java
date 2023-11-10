package com.mxs.common.util;

/**
 * 时间格式化枚举
 * Created by j.yang on 2019-06-16.
 */
public enum DateTimeFormatStrEnum {
    TIME_FORMAT_SPLIT_DAY("yyyy-MM-dd"),
    TIME_FORMAT_SPLIT_HOUR("yyyy-MM-dd HH"),
    TIME_FORMAT_SPLIT_MINUTE("yyyy-MM-dd HH:mm"),
    TIME_FORMAT_SPLIT_SECOND("yyyy-MM-dd HH:mm:ss"),
    TIME_FORMAT_SPLIT_MILLISECOND("yyyy-MM-dd HH:mm:ss.SSS"),
    TIME_FORMAT_MILLISECOND("yyyyMMddHHmmssSSS"),
    TIME_FORMAT_SECOND("yyyyMMddHHmmss"),
    TIME_FORMAT_MINUTE("yyyyMMddHHmm"),
    TIME_FORMAT_HOUR("yyyyMMddHH"),
    DATE_FORMAT_DAY("yyyyMMdd");
    private final String formatStr;

    public String getFormatStr() {
        return this.formatStr;
    }

    private DateTimeFormatStrEnum(String formatStr) {
        this.formatStr = formatStr;
    }

}
