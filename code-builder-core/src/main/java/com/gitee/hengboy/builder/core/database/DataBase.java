package com.gitee.hengboy.builder.core.database;
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
import com.gitee.hengboy.builder.core.database.model.Column;
import com.gitee.hengboy.builder.core.database.model.Table;

import java.sql.Connection;
import java.util.List;

/**
 * 数据库实例接口
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:23 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public interface DataBase {
    /**
     * 获取数据库的连接
     *
     * @return 数据库连接对象
     */
    Connection getConnection();

    /**
     * 关闭数据库连接
     */
    void closeConnection();

    /**
     * 获取数据库内的所有数据表
     *
     * @param tableNamePattern 表名称表达式过滤，如：sys_%，则仅仅查询出【sys_】开头的所有表
     * @return 数据表列表
     */
    List<Table> getTables(String tableNamePattern);

    /**
     * 获取表名称列表
     *
     * @param tableNamePattern 获取表名时使用的表达式
     * @return 表名列表
     */
    List<String> getTableNames(String tableNamePattern);

    /**
     * 根据表名获取数据表对象
     *
     * @param tableName 表名
     * @return 表对象实例
     */
    Table getTable(String tableName);

    /**
     * 查询表内的全部列表
     *
     * @param tableName 表名
     * @return 数据列列表
     */
    List<Column> getColumns(String tableName);

    /**
     * 查询表内
     *
     * @param tableName 表名
     * @return 主键列表
     */
    List<Column> getPrimaryKeys(String tableName);

    /**
     * 是否为主键列
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 是否为主键，true：主键，false：非主键
     */
    boolean isPrimaryKey(String tableName, String columnName);

    /**
     * 是否为外键
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 是否外键，true：外键，false：非外键
     */
    boolean isForeignKey(String tableName, String columnName);

    /**
     * 获取表备注信息
     *
     * @param tableName 表名
     * @return 表备注信息
     */
    String getTableComment(String tableName);
}
