package com.platform.system.common.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 驼峰法-下划线互转
 */
public class TransferCamelUtil {

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    private static final Pattern CAMEL_UNDERLINE_DATABASE = Pattern.compile("(([A-Z]([a-z]+))|([0-9]+))?");

    public static String camel2Underline4Database(String line, char splitChar, boolean isUpperCase) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Matcher matcher = CAMEL_UNDERLINE_DATABASE.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word);
            sb.append(matcher.end() == line.length() ? "" : splitChar);
        }
        String result;
        if (isUpperCase) {
            result = sb.toString().toUpperCase();
        } else {
            result = sb.toString().toLowerCase();
        }
        return result;
    }

    /**
     * 将驼峰命名的字符串用下划线"_"分隔开,
     *
     * @param line
     * @return
     */
    public static String camel2Underline4Database(String line, boolean isUpperCase) {
        return camel2Underline4Database(line, '_', isUpperCase);
    }

    /**
     * 将驼峰命名的字符串用下划线"_"分隔开,最后的字符都为小写。
     *
     * @param line
     * @return
     */
    public static String camel2Underline4Database(String line) {
        return camel2Underline4Database(line, false);
    }

    public static void main(String[] args) {
        String line = "UNBIND_TIME";
        String camel = underline2Camel(line, true);
        System.out.println(camel);
        System.out.println(camel2Underline(camel));
    }
}