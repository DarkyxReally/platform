package com.platform.system.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * @version: 1.0
 */
public class DateUtils {

    private DateUtils() {
    }

    private static SimpleDateFormat SDF_TYPE_LINE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 返回当前时间，返回类型为LocalDateTime
     *
     * @return
     */
    public static LocalDateTime getCurLocalDateTime() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }

    /**
     * 将时间字符串转换为LocalDateTime对象，如："2017-12-12 15:37:59"
     * 注意：字符串的格式必须与DateTimeFormat格式对应
     *
     * @param dateTimeStr
     * @param format
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, DateTimeFormat format) {
        return LocalDateTime.parse(dateTimeStr, format.formatter);
    }

    /**
     * 将日期字符串转换为LocalDate对象，如："2017-12-12" 注意：字符串的格式必须与DateFormat格式对应
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static LocalDate parseLocalDate(String dateStr, DateFormat format) {
        return LocalDate.parse(dateStr, format.formatter);
    }

    /**
     * 将时间字符串转换为Date对象，如："2017-12-12 15:37:59" 注意：字符串的格式必须与DateTimeFormat格式对应
     *
     * @param dateTimeStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateTimeStr, DateTimeFormat format) {
        return toDate(LocalDateTime.parse(dateTimeStr, format.formatter));
    }

    /**
     * 将日期字符串转换为Date对象
     *
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return
     */
    public static Date parseDate(String dateStr, String pattern) {
        return toDate(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern)));
    }

    /**
     * @des: 将日期字符串转换为精确Date对象
     * @param: [dateStr 日期字符串, pattern 格式]
     * @version: 2.5.2
     */
    public static Date parse(String dateStr, String pattern) {
        Date date = null;
        try {
            switch (pattern) {
                case DateTimeFormat.TYPE_LINE:
                    date = SDF_TYPE_LINE.parse(dateStr);
                    break;
                default:
                    break;
            }
        } catch (ParseException e) {

        }
        return date;
    }


    /**
     * 将日期字符串转换为Date对象，如："2017-12-12" 注意：字符串的格式必须与DateFormat格式对应
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, DateFormat format) {
        return toDate(LocalDate.parse(dateStr, format.formatter));
    }

    /**
     * 将LocalDateTime对象转换为字符串
     *
     * @param localDateTime
     * @param format
     * @return
     */
    public static String format(LocalDateTime localDateTime, DateTimeFormat format) {
        return format.formatter.format(localDateTime);
    }

    /**
     * 将LocalDate对象转换为字符串
     *
     * @param localDate
     * @param format
     * @return
     */
    public static String format(LocalDate localDate, DateFormat format) {
        return format.formatter.format(localDate);
    }

    /**
     * 将时间转换为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, DateTimeFormat format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format.toString());
        return sdf.format(date);
        // return
        // format(LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault()),
        // format);
    }

    public static String format(Date date, String pattern) {
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 将日期转换为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, DateFormat format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format.toString());
        return sdf.format(date);
    }

    /**
     * 返回当前时间，返回类型为Date
     *
     * @return
     */
    public static Date getCurDate() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime z = now.atZone(ZoneId.systemDefault());
        return Date.from(z.toInstant());
    }

    /***
     * 获取第二天的凌晨时间
     *
     * @return
     */
    public static Date getAfterDayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /***
     * 获取上个月第一天的凌晨时间
     *
     * @return
     */
    public static Date getLastMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某月第一天的凌晨时间
     *
     * @param year
     * @param month 1-12
     * @return
     */
    public static Date getFirstDateByMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastDateByMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 将LocalDateTime转换为Date
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDate转换为Date
     *
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * date2-date1 = 多少天
     *
     * @param date1
     * @param date2 一般date1：旧时间，date2：新时间（如现在时间）
     * @return
     */
    public static long getDiffersDays(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            long daysBetween = (d2.getTime() - d1.getTime() + 1000000) / (3600 * 24 * 1000);
            return daysBetween;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * date2-date1 = 多少天(统计到结尾时间末)
     *
     * @param date1
     * @param date2 一般date1：旧时间，date2：新时间（如现在时间）
     * @return
     */
    public static long getNewDiffersDays(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            long daysBetween = (d2.getTime() - d1.getTime() + 1000000) / (3600 * 24 * 1000);
            return daysBetween;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * date2-date1 = 多少天
     *
     * @param d1
     * @param d2 一般date1：旧时间，date2：新时间（如现在时间）
     * @return
     */
    public static long getDiffersDays(Date d1, Date d2) {
        try {
            long daysBetween = (d2.getTime() - d1.getTime() + 1000000) / (3600 * 24 * 1000);
            return daysBetween;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个时间相差多少小时
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static long getDiffersHours(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        return (time2 - time1) / (1000 * 3600);
    }

    /**
     * 计算两个时间相差多少秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDiffersSeconds(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / 1000;
    }

    /**
     * 计算两个时间相差多少毫秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDiffersMiliseconds(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }

    /**
     * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
     *
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date getDate(String dateTimeStr, String formatStr) {
        try {
            if (dateTimeStr == null || "".equals(dateTimeStr)) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            java.util.Date d = sdf.parse(dateTimeStr);
            return d;
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 时间 加 操作
     *
     * @param date
     * @param timeUnit   时间单位
     * @param i          增加数值
     * @param dateFormat 返回结果的日期格式
     * @return
     */
    public static String dateAdd(Date date, TimeUnit timeUnit, int i, DateFormat dateFormat) {
        Date dateAdd = dateAdd(date, timeUnit, i);
        return format(dateAdd, dateFormat);
    }

    /**
     * 时间 加 操作
     *
     * @param date
     * @param timeUnit   时间单位
     * @param i          增加数值
     * @param dateFormat 返回结果的日期格式
     * @return
     */
    public static String dateAdd(Date date, TimeUnit timeUnit, int i, DateTimeFormat dateFormat) {
        Date dateAdd = dateAdd(date, timeUnit, i);
        return format(dateAdd, dateFormat);
    }

    /**
     * @param date
     * @param day  想要获取的日期与传入日期的差值 比如想要获取传入日期前四天的日期 day=-4即可
     * @return
     */
    public static Date getSomeDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * @param date
     * @param day  想要获取的日期与传入日期的差值 比如想要获取传入日期前四天的日期 day=-4即可
     * @return
     */
    public static String getSomeDayString(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());

    }

    /**
     * 时间 加 操作
     *
     * @param date
     * @param timeUnit 时间单位
     * @param i        增加数值
     * @param format   返回结果的日期格式
     * @return
     */
    public static String dateAdd(Date date, TimeUnit timeUnit, int i, String format) {
        Date dateAdd = dateAdd(date, timeUnit, i);
        return new SimpleDateFormat(format).format(dateAdd);
    }

    /**
     * 时间 加 操作
     *
     * @param date
     * @param timeUnit 时间单位
     * @param i        增加数值
     * @return
     */
    public static Date dateAdd(Date date, TimeUnit timeUnit, int i) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        c.setTime(date);
        switch (timeUnit) {
            case SECONDS:
                int s = c.get(Calendar.SECOND);
                s = s + i;
                c.set(Calendar.SECOND, s);
                break;
            case MINUTES:
                int m = c.get(Calendar.MINUTE); // 取出“分钟”数
                m = m + i;
                c.set(Calendar.MINUTE, m); // 将“分钟”数设置回去
                break;
            case HOURS:
                int h = c.get(Calendar.HOUR_OF_DAY); // 取出“小时”数
                h = h + i;
                c.set(Calendar.HOUR_OF_DAY, h); // 将“小时”数设置回去
                break;
            case DAYS:
                int d = c.get(Calendar.DAY_OF_MONTH); // 取出“日”数
                d = d + i;
                c.set(Calendar.DAY_OF_MONTH, d); // 将“日”数设置回去
                break;
            default:
                throw new IllegalArgumentException("时间单位不支持");
        }
        return c.getTime();
    }

    /**
     * 加天数    返回日期格式   yyyy-MM-dd 23:59:59
     *
     * @return
     */
    public static Date getVIPAddDate(Date date, int i) {
        Date dateAdd = dateAdd(date, TimeUnit.DAYS, i);
        String format = new SimpleDateFormat(DateTimeFormat.TYPE_LINE_END).format(dateAdd);
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.TYPE_LINE);
        try {
            return sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加天数   返回日期格式   yyyy-MM-dd 23:59:59
     *
     * @return Date
     */
    public static Date getVIPAddDate2(Date date, int i) {
        Date dateAdd = dateAdd(date, TimeUnit.DAYS, i);
        return getLastSecondOverDate(dateAdd);

    }


    /**
     * 时间 加 操作
     *
     * @param date
     * @param type   时间类型 s:秒 d:天 h:时 m:分
     * @param i      增加数值
     * @param format 格式
     * @return
     */
    public static String dateAdd(String date, String type, int i, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        String str = date;
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        c.setTime(getDate(date, format));
        if ("s".equals(type)) {
            int s = c.get(Calendar.SECOND);
            s = s + i;
            c.set(Calendar.SECOND, s);
            str = df.format(c.getTime());
        } else if ("d".equals(type)) {
            int d = c.get(Calendar.DAY_OF_MONTH); // 取出“日”数
            d = d + i;
            c.set(Calendar.DAY_OF_MONTH, d); // 将“日”数设置回去
            str = df.format(c.getTime());
        } else if ("h".equals(type)) {
            int d = c.get(Calendar.HOUR_OF_DAY); // 取出“日”数
            d = d + i;
            c.set(Calendar.HOUR_OF_DAY, d); // 将“日”数设置回去
            str = df.format(c.getTime());
        } else if ("m".equals(type)) {
            int d = c.get(Calendar.MINUTE); // 取出“分钟”数
            d = d + i;
            c.set(Calendar.MINUTE, d); // 将“日”数设置回去
            str = df.format(c.getTime());
        }
        return str;
    }

    /**
     * 比较指定两日期,如果dateStr2晚于dateStr1则return true;
     *
     * @param dateStr1 指定日期
     * @param dateStr2 指定日期
     * @param pattern  指定日期的格式
     * @return boolean
     */
    public static boolean dateCompare(String dateStr1, String dateStr2, String pattern) {
        boolean bea = false;
        SimpleDateFormat sdf_d = new SimpleDateFormat(pattern);
        java.util.Date date1;
        java.util.Date date0;
        try {
            date1 = sdf_d.parse(dateStr1);
            date0 = sdf_d.parse(dateStr2);
            if (date0.after(date1)) {
                bea = true;
            }
        } catch (ParseException e) {
            bea = false;
        }
        return bea;
    }

    /**
     * 日期格式化枚举类
     *
     * @since: 2017年12月12日下午3:30:25
     * @version: 1.0
     */
    public enum DateFormat {
        /**
         * yyyy-MM-dd
         */
        LINE(DateFormat.TYPE_LINE),
        /**
         * yyyy/MM/dd
         */
        SLASH(DateFormat.TYPE_SLASH),
        /**
         * yyyyMMdd
         */
        NONE(DateFormat.TYPE_NONE),
        /**
         *
         */
        UNIT(DateFormat.TYPE_UNIT);

        public static final String TYPE_LINE = "yyyy-MM-dd";
        public static final String TYPE_SLASH = "yyyy/MM/dd";
        public static final String TYPE_NONE = "yyyyMMdd";
        public static final String TYPE_UNIT = "yyyy年MM月dd日";

        private final String pattern;

        private transient DateTimeFormatter formatter;

        DateFormat(String pattern) {
            this.pattern = pattern;
            formatter = DateTimeFormatter.ofPattern(pattern);
        }

        @Override
        public String toString() {
            return pattern;
        }
    }

    /**
     * 时间格式化枚举类
     *
     * @since: 2017年12月12日下午3:30:44
     * @version: 1.0
     */
    public enum DateTimeFormat {
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        LINE(DateTimeFormat.TYPE_LINE),


        LINE_END(DateTimeFormat.TYPE_LINE_END),
        /**
         * yyyy/MM/dd HH:mm:ss
         */
        SLASH(DateTimeFormat.TYPE_SLASH),
        /**
         * yyyyMMddHHmmssSSS
         */
        MSEL_STR_FORMAT(DateTimeFormat.TYPE_MSEL_STR_FORMAT),
        /**
         * yyyy年MM月dd日
         */
        YEAR_MATCH_DAY_(DateTimeFormat.YEAR_MATCH_DAY),
        /**
         * yyyyMMddHHmmss
         */
        NONE(DateTimeFormat.TYPE_NONE),
        /**
         * yyyyMMdd
         */
        YEARMONTHDAY_(DateTimeFormat.YEARMONTHDAY);

        public static final String TYPE_LINE = "yyyy-MM-dd HH:mm:ss";
        public static final String TYPE_SLASH = "yyyy/MM/dd HH:mm:ss";
        public static final String TYPE_MSEL_STR_FORMAT = "yyyyMMddHHmmssSSS";
        public static final String TYPE_NONE = "yyyyMMddHHmmss";
        public static final String YEARMONTHDAY = "yyyyMMdd";
        public static final String YEAR_MATCH_DAY = "yyyy年MM月dd日";

        public static final String TYPE_LINE_END = "yyyy-MM-dd 23:59:59";

        private final String pattern;
        private transient DateTimeFormatter formatter;

        DateTimeFormat(String pattern) {
            this.pattern = pattern;
            formatter = DateTimeFormatter.ofPattern(pattern);
        }

        @Override
        public String toString() {
            return pattern;
        }
    }

    /***
     * 获取指定月份的第一天的日期
     *
     * @return
     */
    public static Date getFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /***
     * 获取指定月份的最后一天的日期
     *
     * @return
     */
    public static Date getLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取本月第一天00:00:00.000
     *
     * @return
     */
    public static Date getFirstDayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取指定时间的所在月第一天00:00:00.000
     * @param date
     * @return
     */
    public static Date getFirstDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取次月第一天00:00:00
     *
     * @return
     */
    public static Date getNextMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取指定时间的次月第一天00:00:00.000
     * @param date
     * @return
     */
    public static Date getNextMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取本月最后一天 23:59:59
     *
     * @return
     */
    public static Date getLastDayOver() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    
    /**
     * 获取指定时间的所在月的最后一天 23:59:59.999
     * @param date
     * @return
     */
    public static Date getLastDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 月份+1
        calendar.add(Calendar.MONTH, 1);
        // 前一天
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 判断两日期是否同在一个月份之中
     *
     * @param from
     * @param to
     * @return
     */
    public static boolean isSameMonth(Date from, Date to) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(from);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(to);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    /**
     * 将时间转换为时间戳
     *
     */
    public static String dateToStamp(String time) {
        Date date = DateUtils.parseDate(time, DateTimeFormat.LINE);
        long ts = date.getTime();
        return String.valueOf(ts);
    }


    /**
     * 是否在时间范围之内
     *
     * @param date
     * @param start
     * @param end
     * @return
     */
    public static final boolean isBetween(Date date, Date start, Date end) {
        long dateTime = date.getTime();
        return dateTime >= start.getTime() && dateTime <= end.getTime();
    }

    /**
     * 以某个时间为基准的当天时间范围
     *
     * @param datetime
     * @return
     */
    public static Date[] getOneDayDateRange(Date datetime) {
        return DateUtils.getDateRange(datetime, DateRange.DAY, 0);
    }

    /**
     * 以当前时间（时分秒毫秒均设置为0）为基准，获取周期时间范围 <br>
     * 如当前时间为: 20180306 14:00:00 <br>
     * 按日类型 获取3天后的时间,返回20180309 00:00:00 - 20180310 00:00:00 <br>
     * 按周类型 获取3周后的时间,返回20180325 00:00:00 - 20180401 00:00:00
     *
     * @param type    类型
     * @param cycleNo 周期（整数,当期为0,上一个周期为-1, 下一个周期为1, 以此类推）
     *                昨天(0点至24点)、上周(星期一至星期日)、上月(1号0点至月底最后一天24点)、去年(去年1月1号至最后一天24点)
     * @return Date[0] 开始时间(较小时间)（闭区间） Date[1] 结束时间(较大时间)(开区间)
     */
    public static Date[] getDateRange(DateRange type, int cycleNo) {
        Calendar cal = Calendar.getInstance();
        return getDateRangeByCalendar(cal, type, cycleNo);
    }

    /**
     * 以某个时间（时分秒毫秒均设置为0）为基准，获取周期时间范围 <br>
     * 如当前时间为: 20180306 14:00:00 <br>
     * 按日类型 获取3天后的时间,返回20180309 00:00:00 - 20180310 00:00:00 <br>
     * 按周类型 获取3周后的时间,返回20180325 00:00:00 - 20180401 00:00:00
     *
     * @param date    时间
     * @param type    类型
     * @param cycleNo 周期（整数,当期为0,上一个周期为-1, 下一个周期为1, 以此类推）
     *                昨天(0点至24点)、上周(星期一至星期日)、上月(1号0点至月底最后一天24点)、去年(去年1月1号至最后一天24点)
     * @return Date[0] 开始时间(较小时间)（闭区间） Date[1] 结束时间(较大时间)(开区间)
     */
    public static Date[] getDateRange(Date date, DateRange type, int cycleNo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getDateRangeByCalendar(cal, type, cycleNo);
    }

    /**
     * 以某个时间（时分秒毫秒均设置为0）为基准，获取周期时间范围 <br>
     * 如当前时间为: 20180306 14:00:00 <br>
     * 按日类型 获取3天后的时间,返回20180309 00:00:00 - 20180310 00:00:00 <br>
     * 按周类型 获取3周后的时间,返回20180325 00:00:00 - 20180401 00:00:00
     *
     * @param cal     时间
     * @param type    类型
     * @param cycleNo 周期（整数,当期为0,上一个周期为-1, 下一个周期为1, 以此类推）
     *                昨天(0点至24点)、上周(星期一至星期日)、上月(1号0点至月底最后一天24点)、去年(去年1月1号至最后一天24点)
     * @return Date[0] 开始时间(较小时间)（闭区间） Date[1] 结束时间(较大时间)(开区间)
     */
    private static Date[] getDateRangeByCalendar(Calendar cal, DateRange type, int cycleNo) {
        // 截至0点0分0秒0毫秒
        cal.set(Calendar.HOUR_OF_DAY, 0); // 24小时制
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date[] dt = new Date[2];
        Integer dateField = null;
        if (DateRange.DAY == type) {
            dateField = Calendar.DATE;
        } else if (DateRange.WEEK == type) {
            cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置第一周的第一天为星期天
            if (cal.get(Calendar.WEEK_OF_YEAR) == 1 && cal.get(Calendar.MONTH) == 11) { // 跨年周的情况
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
            }
            int typeNum = cal.get(Calendar.WEEK_OF_YEAR);
            cal.set(Calendar.WEEK_OF_YEAR, typeNum);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 周日

            dateField = Calendar.WEEK_OF_YEAR;
        } else if (DateRange.MONTH == type) {
            cal.set(Calendar.DAY_OF_MONTH, 1);
            dateField = Calendar.MONTH;
        } else if (DateRange.YEAR == type) {
            cal.set(Calendar.MONTH, 0); // 一月一号
            cal.set(Calendar.DAY_OF_MONTH, 1);

            dateField = Calendar.YEAR;
        } else {
            throw new IllegalArgumentException("不支持的类型");
        }

        cal.add(dateField, cycleNo);

        if (cycleNo < 0) {
            cal.add(dateField, 1);
            dt[1] = cal.getTime();
            cal.add(dateField, -1);
            dt[0] = cal.getTime();
        } else {
            dt[0] = cal.getTime(); // 开始时间
            cal.add(dateField, 1);
            dt[1] = cal.getTime(); // 结束时间
        }

        return dt;
    }

    /**
     * 日期范围类型
     *
     * @version: 1.0
     */
    public enum DateRange {
        DAY, WEEK, MONTH, YEAR;
    }

    /**
     * 今天凌晨0点 -临时
     *
     * @return
     */
    public static Date getTodayZeroDate() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 今天23:59:59
     *
     * @return
     */
    public static Date getTodayOverDate() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 23);
        todayStart.set(Calendar.MINUTE, 59);
        todayStart.set(Calendar.SECOND, 59);
        todayStart.set(Calendar.MILLISECOND, 999);
        return todayStart.getTime();
    }


    /**
     * 转换一个date为yyyy-MM-dd 23:59:59 date
     *@param date
     * @return Date
     */
    public static Date getLastSecondOverDate(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 23);
        todayStart.set(Calendar.MINUTE, 59);
        todayStart.set(Calendar.SECOND, 59);
        todayStart.set(Calendar.MILLISECOND, 999);
        return todayStart.getTime();
    }

    /**
     * 获取指定时间的之前第几天时间
     *
     * @param date 指定时间
     * @param days 第一天之前 eg: 1: 指定时间的前一天时间
     * @return
     */
    public static Date getBeforeDaysDate(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        Date d = c.getTime();
        return d;
    }

    /**
     * 获取两个日期之间的月份
     *
     * @param start
     * @param end
     * @return
     */
    public static int getMonth(Date start, Date end) {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(start);
        aft.setTime(end);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 获得月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 为指定日期添加值
     *
     * @param date
     * @param calendarField
     * @param amount
     * @return
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 添加月份
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    /**
     * 判断两个日期是否同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    /**
     * 获取指定时间的之后的第几天时间
     *
     * @param date 指定时间
     * @param days 第一天z之后 eg: 1: 指定时间的明天
     * @return
     */
    public static Date getAfterDaysDate(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        Date d = c.getTime();
        return d;
    }

    /**
     * 获取N个小时前时间
     *
     * @param date
     * @return
     */
    public static Date getOneHourBeforeDate(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - hour);
        return calendar.getTime();
    }

    /**
     * 获取N分钟后时间时间
     *
     * @param date
     * @return
     */
    public static Date getOneMinuteAfterDate(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);
        return calendar.getTime();
    }

    /**
     * 获取本月剩余天数
     *
     * @return
     */
    public static int getCurMonthRemainingDays() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int last = c.getActualMaximum(Calendar.DAY_OF_MONTH);    //获取本月最大天数
        return last - day;
    }


    /**
     * 获取当前为星期几 (1=星期一, 7=星期天)
     *
     * @return
     */
    public static int getDayOfWeek() {
        Calendar now = Calendar.getInstance();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 获取本周的结束时间
     *
     * @return
     */
    public static Date getEndDayOfWeek() {
        Date beginDay = getBeginDayOfWeek();
        Date endDay = dateAdd(beginDay, TimeUnit.SECONDS, -1);
        return dateAdd(endDay, TimeUnit.DAYS, 7);
    }

    /**
     * 以某个时间为基准的周的时间范围
     * 按照周一为第一天, 周日为最后一天
     *
     * @param date
     * @param cycleNo 周期（整数,当期为0,上一个周期为-1, 下一个周期为1, 以此类推）
     * @return Date[0] 开始时间(周一 00:00:00)（闭区间） Date[1] 结束时间(周日 23:59:59)
     */
    public static Date[] getWeekRange(Date date, int cycleNo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date[] range = getDateRangeByCalendar(cal, DateRange.WEEK, cycleNo);
        Date start = range[0];
        Date end = range[1];
        start = dateAdd(start, TimeUnit.DAYS, 1);
        end = dateAdd(end, TimeUnit.DAYS, 1);
        end = dateAdd(end, TimeUnit.SECONDS, -1);
        range[0] = start;
        range[1] = end;
        return range;
    }

    /**
     * 根据年份和月份获取当前月有多少日（天数）
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByMonth(Integer year, Integer month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取某天的凌晨时间(00:00:00)
     *
     * @param year  某年
     * @param month 某月
     * @param day   某日
     * @return
     */
    public static Date getZeroDateByDay(Integer year, Integer month, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取指定时间的凌晨时间(00:00:00)
     * @param dateTime
     * @return
     */
    public static Date getZeroDateByDay(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某天的结束时间（23：59：59）
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getOverDateByDay(Integer year, Integer month, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static void main(String[] args) {
//        System.out.println(getLastDay(new Date()));
        System.out.println(format(new Date(),DateTimeFormat.YEAR_MATCH_DAY_));
        System.out.println(DateUtils.dateAdd(new Date(), TimeUnit.DAYS,0));

        System.out.println(format(getTodayOverDate(),DateTimeFormat.TYPE_SLASH));
    }
}
