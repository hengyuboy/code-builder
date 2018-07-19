package com.gitee.hengboy.builder.core.database;
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
import com.gitee.hengboy.builder.common.enums.ErrorEnum;
import com.gitee.hengboy.builder.common.enums.TableMetaDataEnum;
import com.gitee.hengboy.builder.common.exception.CodeBuilderException;
import com.gitee.hengboy.builder.common.util.StringUtil;
import com.gitee.hengboy.builder.core.database.model.Column;
import com.gitee.hengboy.builder.core.database.model.Table;
import com.gitee.hengboy.builder.core.database.model.util.JavaTypeResolver;
import com.gitee.hengboy.builder.core.database.model.util.JdbcTypeResolver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象数据库实例类
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:24 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public abstract class AbstractDataBase implements DataBase {
    /**
     * 数据库连接对象
     */
    protected Connection connection;
    /**
     * 代码生成器参数配置实例
     */
    protected CodeBuilderProperties codeBuilderProperties;

    /**
     * 构造函数传递参数配置实例
     *
     * @param codeBuilderProperties 生成所需参数对象
     */
    public AbstractDataBase(CodeBuilderProperties codeBuilderProperties) {
        this.codeBuilderProperties = codeBuilderProperties;
        // 获取数据库连接
        getConnection();
    }

    /**
     * 获取数据库连接对象
     *
     * @return 数据库连接对象
     */
    public Connection getConnection() {
        try {
            /*
             * 连接不存在 || 连接失效
             * 重新获取连接
             */
            if (connection == null || connection.isClosed()) {
                // spring-boot-starter方式获取数据库连接
                if (codeBuilderProperties.getDataSource() != null) {
                    connection = codeBuilderProperties.getDataSource().getConnection();
                }
                // maven-plugin插件形式获取数据库连接
                else {
                    // 默认使用数据库驱动类型内的限定类名
                    String driverClassName = codeBuilderProperties.getDbType().getValue();
                    // 存在自定义的驱动限定类名时使用自定义来实例化驱动对象
                    if (StringUtil.isNotEmpty(codeBuilderProperties.getDbDriverClassName())) {
                        driverClassName = codeBuilderProperties.getDbDriverClassName();
                    }
                    //加载驱动程序
                    Class.forName(driverClassName);
                    // 获取数据库连接
                    connection = DriverManager.getConnection(codeBuilderProperties.getDbUrl(), codeBuilderProperties.getDbUserName(), codeBuilderProperties.getDbPassword());
                }
            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new CodeBuilderException(ErrorEnum.NOT_GET_CONNECTION);
    }

    /**
     * 关闭数据库连接对象
     */
    public void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取表名列表
     *
     * @param tableNamePattern 表名表达式
     * @return 表名列表
     */
    public List<String> getTableNames(String tableNamePattern) {
        try {
            // 获取该数据库内的所有表
            ResultSet resultSet = connection.getMetaData().getTables(connection.getCatalog(), codeBuilderProperties.getDbUserName(), tableNamePattern, new String[]{"TABLE"});
            List<String> tables = new ArrayList<String>();
            while (resultSet.next()) {
                // 获取表名
                String tableName = resultSet.getString(TableMetaDataEnum.TABLE_NAME.getValue());
                // 获取表格基本信息
                tables.add(tableName);
            }
            return tables;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new CodeBuilderException(ErrorEnum.NOT_GET_TABLE);
    }

    /**
     * 获取数据库内的所有表
     *
     * @param tableNamePattern 表名表达式
     * @return 数据表列表结果集
     */
    public List<Table> getTables(String tableNamePattern) {
        try {
            // 获取该数据库内的所有表
            ResultSet resultSet = connection.getMetaData().getTables(connection.getCatalog(), codeBuilderProperties.getDbUserName(), tableNamePattern, new String[]{"TABLE"});
            List<Table> tables = new ArrayList<Table>();
            while (resultSet.next()) {
                // 获取表名
                String tableName = resultSet.getString(TableMetaDataEnum.TABLE_NAME.getValue());
                // 获取表格基本信息
                tables.add(getTable(tableName));
            }
            return tables;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new CodeBuilderException(ErrorEnum.NOT_GET_TABLE);
    }

    /**
     * 获取数据表基本信息
     *
     * @param tableName 表名
     * @return 数据表对象实例
     */
    public Table getTable(String tableName) {
        // 构建数据表对象
        Table.TableBuilder tableBuilder = Table.builder();
        try {
            ResultSet resultSet = getConnection().getMetaData().getTables(null, null, tableName, new String[]{"TABLE"});
            if (resultSet.next()) {
                tableBuilder
                        // 表名
                        .tableName(tableName)
                        // 实体类名称
                        .entityName(StringUtil.getCamelCaseString(tableName, true))
                        // 表类别
                        .catalog(resultSet.getString(TableMetaDataEnum.TABLE_CAT.getValue()))
                        // 表模式
                        .schema(resultSet.getString(TableMetaDataEnum.TABLE_SCHEMA.getValue()))
                        // 表类型
                        .tableType(resultSet.getString(TableMetaDataEnum.TABLE_TYPE.getValue()))
                        // 获取备注信息
                        .remark(getTableComment(tableName))
                        // 所有列,排除主键
                        .columns(getColumns(tableName))
                        // 主键列表
                        .primaryKeys(getPrimaryKeys(tableName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableBuilder.build().buildAfterSetting(codeBuilderProperties);
    }

    /**
     * 根据结果集获取列的详细信息
     * 根据不同的columnPattern获取到的列元数据
     *
     * @param resultSet       结果集
     * @param isPrimaryColumn true：主键列，false：普通列
     * @return 列基本信息对象
     */
    private Column getColumn(ResultSet resultSet, boolean isPrimaryColumn) {
        try {
            // 数据库字段类型
            int jdbcType = resultSet.getInt(TableMetaDataEnum.DATA_TYPE.getValue());
            // 列名
            String columnName = resultSet.getString(TableMetaDataEnum.COLUMN_NAME.getValue());
            // 表名
            String tableName = resultSet.getString(TableMetaDataEnum.TABLE_NAME.getValue());

            return Column.builder()
                    // 列名
                    .columnName(columnName)
                    // 列长度
                    .size(resultSet.getInt(TableMetaDataEnum.COLUMN_SIZE.getValue()))
                    // 是否为空
                    .nullable(resultSet.getBoolean(TableMetaDataEnum.NULLABLE.getValue()))
                    // 默认值
                    .defaultValue(resultSet.getString(TableMetaDataEnum.COLUMN_DEF.getValue()))
                    // 数据库列类型
                    .jdbcType(jdbcType)
                    // 是否自增
                    .autoincrement(hasColumn(resultSet, TableMetaDataEnum.IS_AUTOINCREMENT.getValue()) ? resultSet.getBoolean(TableMetaDataEnum.IS_AUTOINCREMENT.getValue()) : false)
                    // 列备注信息
                    .remark(resultSet.getString(TableMetaDataEnum.REMARKS.getValue()))
                    // 精度
                    .decimalDigits(resultSet.getInt(TableMetaDataEnum.DECIMAL_DIGITS.getValue()))
                    // jdbc类型名称
                    .jdbcTypeName(JdbcTypeResolver.getJdbcTypeName(jdbcType))
                    // 格式化后的java field
                    .javaProperty(StringUtil.getCamelCaseString(columnName, false))
                    // 对应的java数据类型
                    .javaType(JavaTypeResolver.getJavaType(jdbcType, false))
                    // java数据类型
                    .fullJavaType(JavaTypeResolver.getJavaType(jdbcType, true))
                    // 是否为主键列
                    // 如果是主键获取列信息，直接返回true
                    .primaryKey(!isPrimaryColumn ? isPrimaryKey(tableName, columnName) : isPrimaryColumn)
                    // 是否为外键列
                    // 如果是主键获取列信息，直接返回false
                    .foreignKey(!isPrimaryColumn ? isForeignKey(tableName, columnName) : false)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取表内的所有列
     *
     * @param tableName 表名
     * @return 数据列列表
     */
    public List<Column> getColumns(String tableName) {
        List<Column> columns = new ArrayList<Column>();
        try {
            // 获取列信息
            ResultSet resultSet = getConnection().getMetaData().getColumns(null, null, tableName, "%");
            while (resultSet.next()) {
                columns.add(getColumn(resultSet, false));
            }
            return columns;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new CodeBuilderException(ErrorEnum.NOT_GET_COLUMN, tableName);
    }

    /**
     * 验证指定的字段是否为主键
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 是否为主键，true：主键，false：非主键
     */
    public boolean isPrimaryKey(String tableName, String columnName) {
        try {
            // 获取表内的主键列表
            ResultSet resultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
            while (resultSet.next()) {
                // 获取主键的列名
                String pkColumnName = resultSet.getString(TableMetaDataEnum.COLUMN_NAME.getValue());
                if (columnName.equals(pkColumnName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证指定列是否为外键
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 是否为外键，true：外键，false：非外键
     */
    public boolean isForeignKey(String tableName, String columnName) {
        try {
            // 获取表内的外键列表
            ResultSet resultSet = connection.getMetaData().getImportedKeys(null, null, tableName);
            while (resultSet.next()) {
                // 获取外键的列名
                String fkColumnName = resultSet.getString(TableMetaDataEnum.FK_COLUMN_NAME.getValue());
                if (columnName.equals(fkColumnName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取表内的主键列表
     *
     * @param tableName 表名
     * @return 主键列表
     */
    public List<Column> getPrimaryKeys(String tableName) {
        try {
            // 获取表内的主键列表
            ResultSet resultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
            List<Column> primaryKeys = new ArrayList<Column>();
            while (resultSet.next()) {
                // 获取主键的列名
                String columnName = resultSet.getString(TableMetaDataEnum.COLUMN_NAME.getValue());
                // 获取主键列的详细信息
                ResultSet columnResultSet = connection.getMetaData().getColumns(null, null, tableName, columnName);
                if (columnResultSet.next()) {
                    // 添加主键信息
                    primaryKeys.add(getColumn(columnResultSet, true));
                }
            }
            return primaryKeys;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new CodeBuilderException(ErrorEnum.NOT_GET_PRIMARY_KEYS, tableName);
    }

    /**
     * 结果集内是否存在自增的列
     *
     * @param rs         结果集
     * @param columnName 自增列名
     * @return true：存在列表，false：不存在列
     * @throws SQLException 数据库异常
     */
    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int columns = resultSetMetaData.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(resultSetMetaData.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }
}
