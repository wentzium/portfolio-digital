package com.mxs.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by j.yang on 2019-06-17.
 */
public class StrUtil {
    public static String toStr(String object) {
        return StringUtils.isEmpty(object) ? "" : object;
    }
}
