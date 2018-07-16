package com.gitee.hengboy.builder.core.database.impl;

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.ErrorEnum;
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
        throw new RuntimeException(String.format(ErrorEnum.NOT_GET_COMMENT.getMessage(), tableName));
    }
}
