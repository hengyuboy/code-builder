package com.gitee.hengboy.builder.common.enums;
/**
 *  Copyright 2018 恒宇少年
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
import lombok.Getter;

/**
 * 数据表元数据枚举
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/9
 * Time：4:46 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Getter
public enum TableMetaDataEnum {
    /**
     * 表类别(可为null)
     */
    TABLE_CAT("TABLE_CAT"),
    /**
     * 表模式（可能为空）,在oracle中获取的是命名空间
     */
    TABLE_SCHEMA("TABLE_SCHEM"),
    /**
     * 表类型
     * 典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"
     */
    TABLE_TYPE("TABLE_TYPE"),
    /**
     * 表名
     */
    TABLE_NAME("TABLE_NAME"),
    /**
     * 列名
     */
    COLUMN_NAME("COLUMN_NAME"),
    /**
     * 外键列表
     */
    FK_COLUMN_NAME("FKCOLUMN_NAME"),
    /**
     * 列大小
     */
    COLUMN_SIZE("COLUMN_SIZE"),
    /**
     * 是否为空
     */
    NULLABLE("NULLABLE"),
    /**
     * 列默认值
     */
    COLUMN_DEF("COLUMN_DEF"),
    /**
     * 列是否自增
     */
    IS_AUTOINCREMENT("IS_AUTOINCREMENT"),
    /**
     * 列数据类型
     */
    DATA_TYPE("DATA_TYPE"),
    /**
     * 列备注信息
     */
    REMARKS("REMARKS"),
    /**
     * 小数位数。对于DECIMAL_DIGITS不适用的数据类型，将返回Null。
     */
    DECIMAL_DIGITS("DECIMAL_DIGITS"),
    /**
     * 表中列的索引（从1开始）
     */
    ORDINAL_POSITION("ORDINAL_POSITION");

    private String value;

    TableMetaDataEnum(String value) {
        this.value = value;
    }
}
