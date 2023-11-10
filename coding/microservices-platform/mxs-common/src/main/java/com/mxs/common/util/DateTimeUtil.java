package com.mxs.common.util;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by j.yang on 2019-06-17.
 */
public class DateTimeUtil {
    public static String format(Date date) {
        if (null == date) {
            return "";
        }
        return new DateTime(date).toString(DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr());
    }

    public static String format(Date date, DateTimeFormatStrEnum formatStr) {
        if (null == date) {
            return "";
        }
        if (null == formatStr) {
            return new DateTime(date).toString(DateTimeFormatStrEnum.TIME_FORMAT_SPLIT_SECOND.getFormatStr());
        }
        return new DateTime(date).toString(formatStr.getFormatStr());
    }

    public static Date strToDate(String dateTimeStr, String formatStr) {
        if (StringUtils.isEmpty(dateTimeStr)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
}
