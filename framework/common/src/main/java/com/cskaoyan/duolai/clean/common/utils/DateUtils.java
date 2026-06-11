package com.cskaoyan.duolai.clean.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间工具类,用于本地时间操作,包含LocalDateTimeUtil的所有方法和自定义的LocalDateTime的操作方法及常量
 */
public class DateUtils extends LocalDateTimeUtil {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT_EN = "yyyy年MM月dd日";
    public static final String DEFAULT_DATE_FORMAT_COMPACT = "yyyyMMdd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String TIME_ZONE_8 = "GMT+8";

    /**
     * 获取utc时间
     *
     * @param localDateTime 转化时间
     * @return utc时间
     */
    public static LocalDateTime getUTCTime(LocalDateTime localDateTime) {
        ZoneId australia = ZoneId.of("Asia/Shanghai");
        ZonedDateTime dateAndTimeInSydney = ZonedDateTime.of(localDateTime, australia);
        ZonedDateTime utcDate = dateAndTimeInSydney.withZoneSameInstant(ZoneOffset.UTC);
        return utcDate.toLocalDateTime();
    }

    /**
     * 获取Asia时间
     *
     * @param localDateTime 转化时间
     * @return Asia时间
     */
    public static LocalDateTime getAsiaTime(LocalDateTime localDateTime) {
        ZoneId australia = ZoneId.of("Asia/Shanghai");
        ZonedDateTime dateAndTimeInSydney = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);
        ZonedDateTime utcDate = dateAndTimeInSydney.withZoneSameInstant(australia);
        return utcDate.toLocalDateTime();
    }




    public static int getDay() {
        return getDay(null);
    }

    public static int getDay(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            localDateTime = now();
        }
        String format = format(localDateTime, DEFAULT_DATE_FORMAT_COMPACT);
        return NumberUtils.parseInt(format);
    }

    /**
     * 获取数字格式的日志
     *
     * @param localDateTime 日期
     * @param format 格式模板，只支持纯数字模板
     * @return
     */
    public static Long getFormatDate(LocalDateTime localDateTime, String format) {
        String date = format(localDateTime, format);
        return date == null ? null : NumberUtils.parseLong(date);
    }


}
