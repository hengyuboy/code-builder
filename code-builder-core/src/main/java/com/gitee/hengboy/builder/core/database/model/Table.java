
package com.gitee.hengboy.builder.core.database.model;

/**
 * Copyright 2018 恒宇少年
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.enums.JavaTypeEnum;
import com.gitee.hengboy.builder.common.util.StringUtil;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 数据表对象
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:28 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Builder
@Data
public class Table {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表驼峰名称
     */
    private String tableCamelName;
    /**
     * 表类型
     */
    private String tableType;
    /**
     * 表别名
     */
    private String tableAlias;
    /**
     * 表备注信息
     */
    private String remark;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * catalog
     */
    private String catalog;
    /**
     * schema
     */
    private String schema;
    /**
     * 数据列列表
     */
    private List<Column> columns;
    /**
     * 主键列表
     */
    private List<Column> primaryKeys;
    /**
     * 是否存在java.sql.Date类型的列
     */
    private boolean hasSqlDate;
    /**
     * 是否存在TimeStamp的列
     */
    private boolean hasTimeStamp;
    /**
     * 是否存在BigDecimal的列
     */
    private boolean hasBigDecimal;

    /**
     * 构建对象后设置是否存在特殊类型的字段
     * 如：java.math.BigDecimal、java.sql.TimeStamp等
     *
     * @param codeBuilderProperties 参数对象
     * @return Table实例
     */
    public Table buildAfterSetting(CodeBuilderProperties codeBuilderProperties) {
        for (Column column : columns) {
            // 是否存在bigDecimal的列
            if (JavaTypeEnum.TYPE_BIG_DECIMAL.getFullName().equals(column.getFullJavaType())) {
                this.hasBigDecimal = true;
            }
            // 是否存在timeStamp的列
            if (JavaTypeEnum.TYPE_TIMESTAMP.getFullName().equals(column.getFullJavaType())) {
                this.hasTimeStamp = true;
            }
            // 是否存在java.sql.Date的列
            if (JavaTypeEnum.TYPE_DATE.getFullName().equals(column.getFullJavaType())) {
                this.hasSqlDate = true;
            }
        }
        // 自动忽略前缀
        if (StringUtil.isNotEmpty(codeBuilderProperties.getIgnoreClassPrefix())) {
            entityName = entityName.replaceFirst(codeBuilderProperties.getIgnoreClassPrefix(), "");
        }
        return this;
    }
}
