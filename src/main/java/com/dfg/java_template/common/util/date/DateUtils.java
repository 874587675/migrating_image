package com.dfg.java_template.common.util.date;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author zgc
 */
@Slf4j
public class DateUtils {
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatTime(long timestamp) {
        return new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS).format(new Date(timestamp));
    }
}
