package com.gitee.hengboy.builder.common;

import lombok.Getter;

/**
 * java类型装载转换枚举
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/11
 * Time：3:43 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Getter
public enum JavaTypeEnum {
    TYPE_BYTE("Byte", "java.lang.Byte"),
    TYPE_SHORT("Short", "java.lang.Short"),
    TYPE_INTEGER("Integer", "java.lang.Integer"),
    TYPE_LONG("Long", "java.lang.Long"),
    TYPE_FLOAT("Float", "java.lang.Float"),
    TYPE_DOUBLE("Double", "java.lang.Double"),
    TYPE_STRING("String", "java.lang.String"),
    TYPE_BOOLEAN("Boolean", "java.lang.Boolean"),
    TYPE_OBJECT("Object", "java.lang.Object"),
    TYPE_DATE("Date", "java.sql.Date"),
    TYPE_TIMESTAMP("Timestamp", "java.sql.Timestamp"),
    TYPE_BYTE_ARRAY("byte[]", "byte[]"),
    TYPE_BIG_DECIMAL("BigDecimal", "java.math.BigDecimal");

    JavaTypeEnum(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    /**
     * 数据类型短名称
     * 如：String
     */
    private String shortName;
    /**
     * 数据类型全名称
     * 如：java.lang.String
     */
    private String fullName;
}
