package com.gitee.hengboy.builder.common;

/**
 * 字符串工具类
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:30 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class StringUtil {
    /**
     * 验证对象是否为空
     *
     * @param object 对象实例
     * @return true：不为空，false：为空
     */
    public static boolean isNotEmpty(Object object) {
        if (object instanceof String) {
            return object != null && String.valueOf(object).length() > 0;
        }
        return object != null;
    }

    /**
     * 转换unicode字符集
     *
     * @param s 包含unicode的字符串
     * @return 格式化后的字符串
     */
    public static String toUnicodeString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                sb.append("\\u" + Integer.toHexString(c));
            }
        }
        return sb.toString();
    }

    /**
     * 格式化驼峰命名字符串
     *
     * @param inputString             未格式化的字符串
     * @param firstCharacterUppercase 首字母是否大写
     * @return 格式化后的字符串
     */
    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }
}
