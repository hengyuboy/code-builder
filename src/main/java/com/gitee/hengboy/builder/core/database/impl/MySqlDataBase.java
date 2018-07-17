package com.gitee.hengboy.builder.core.database.impl;
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
import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.enums.ErrorEnum;
import com.gitee.hengboy.builder.common.exception.CodeBuilderException;
import com.gitee.hengboy.builder.core.database.AbstractDataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySQL数据库类型实体
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:24 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class MySqlDataBase extends AbstractDataBase {
    /**
     * MySQL查询表状态的执行SQL
     */
    private static final String TABLE_COMMENT_SQL = "show table status where NAME=?";
    /**
     * 表备注字段名称
     */
    private static final String TABLE_COMMENT_COLUMN = "COMMENT";

    public MySqlDataBase(CodeBuilderProperties codeBuilderProperties) {
        super(codeBuilderProperties);
    }

    /**
     * 获取表备注信息
     *
     * @param tableName 表名
     * @return 表备注信息
     */
    public String getTableComment(String tableName) {
        try {
            PreparedStatement statement = connection.prepareStatement(TABLE_COMMENT_SQL);
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(TABLE_COMMENT_COLUMN);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new CodeBuilderException(ErrorEnum.NOT_GET_COMMENT, tableName);
    }
}
