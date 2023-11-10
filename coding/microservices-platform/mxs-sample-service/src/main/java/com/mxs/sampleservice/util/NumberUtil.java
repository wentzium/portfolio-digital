package com.mxs.sampleservice.util;

/**
 *  Created by j.yang on 2019-07-22
 * 
 */
public class NumberUtil {

    public static Long nullDefault(Long value, Long defaultValue) {
        if (null == value && null == defaultValue) {
            return 0L;
        }
        if (null == value) {
            return defaultValue;
        }
        return value;
    }

    public static Integer integerNotNull(Integer value, Integer defaultValue) {
        if (null == value && null == defaultValue) {
            return 0;
        }
        if (null == value) {
            return defaultValue;
        }
        return value;
    }
}
