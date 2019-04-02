package com.zpwan.appcommon.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理
 *
 * @author 吴爱军
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 随机字符串范围
     */
    private static final String SOURCES = "0123456789";

    /**
     * 敏感字符加敏处理
     *
     * @param content   字符串内容
     * @param startKeep 保留前几位
     * @param endKeep   保留后几位
     * @return 返回处理后的字符串
     */
    public static String toShowSecret(String content, int startKeep, int endKeep) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(content)) {
            return "";
        }
        int len = content.length();
        int keepLen = startKeep + endKeep;
        if (len <= keepLen) {
            if (startKeep <= len) {
                return content.substring(0, startKeep) + org.apache.commons.lang3.StringUtils.repeat('*', len - startKeep);
            }
            return content;
        } else {
            String startStr = content.substring(0, startKeep);
            String endStr = content.substring(len - endKeep, len);
            return startStr + org.apache.commons.lang3.StringUtils.repeat('*', len - startKeep - endKeep) + endStr;
        }
    }

    public static String format(String content, String... parameters) {
        if (!ArrayUtils.isEmpty(parameters)) {
            for (String parameter : parameters) {
                content = content.replaceFirst("\\{\\}", parameter);
            }
        }
        return content;
    }

    /**
     * 首字母大写
     *
     * @param s
     * @return
     */
    public static String firstCharUpperCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }

    /**
     * 隐藏头几位
     *
     * @param str
     * @param len
     * @return
     */
    public static String hideFirstChar(String str, int len) {
        if (str == null) {
            return null;
        }
        char[] chars = str.toCharArray();
        if (str.length() <= len) {
            for (int i = 0; i < chars.length; i++) {
                chars[i] = '*';
            }
        } else {
            for (int i = 0; i < 1; i++) {
                chars[i] = '*';
            }
        }
        str = new String(chars);
        return str;
    }

    /**
     * 隐藏末几位
     *
     * @param str
     * @param len
     * @return
     */
    public static String hideLastChar(String str, int len) {
        if (str == null) {
            return null;
        }
        char[] chars = str.toCharArray();
        if (str.length() <= len) {
            return hideLastChar(str, str.length() - 1);
        } else {
            for (int i = chars.length - 1; i > chars.length - len - 1; i--) {
                chars[i] = '*';
            }
        }
        str = new String(chars);
        return str;
    }

    /**
     * 指定起始位置字符串隐藏
     *
     * @param str
     * @param index1
     * @param index2
     * @return
     */
    public static String hideStr(String str, int index1, int index2) {
        if (str == null) {
            return null;
        }
        String str1 = str.substring(index1, index2);
        String str2 = str.substring(index2);
        String str3 = "";
        if (index1 > 0) {
            str1 = str.substring(0, index1);
            str2 = str.substring(index1, index2);
            str3 = str.substring(index2);
        }
        char[] chars = str2.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = '*';
        }
        str2 = new String(chars);
        String str4 = str1 + str2 + str3;
        return str4;
    }

    /**
     * 大写字母转成“_”+小写 驼峰命名转换为下划线命名
     *
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        char[] charArr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        sb.append(charArr[0]);
        for (int i = 1; i < charArr.length; i++) {
            if (charArr[i] >= 'A' && charArr[i] <= 'Z') {
                sb.append('_').append(charArr[i]);
            } else {
                sb.append(charArr[i]);
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 生成指定长度的随机字符串，字母加数字组合
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 生成指定长度的随机数字
     *
     * @param length
     * @return
     */
    public static String getRandomIntString(int length) {
        Random rand = new Random();
        StringBuffer randomString = new StringBuffer();
        for (int j = 0; j < length; j++) {
            randomString.append(SOURCES.charAt(rand.nextInt(SOURCES.length() - 1)) + "");
        }
        return randomString.toString();
    }

    /**
     * @Description: 判断是否为正数 包括小数
     * @Author: hzzp
     * @Date: 2018/7/31
     * @param orginal
     * @return: boolean
     */
    public static boolean isPositiveNumber(String orginal){
        String regExp = "^(\\+)?\\d+(\\.\\d+)?$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m = pattern.matcher(orginal);
        return m.matches();
    }
    public static boolean isNumber(String orginal){
        String regExp = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m = pattern.matcher(orginal);
        return m.matches();
    }

    /*public static void main(String[] args){
        String test = "001213123";
        System.out.println(isNumber(test));
    }*/
    public static void main(String[] args) {

    }
}