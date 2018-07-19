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
import com.gitee.hengboy.builder.core.database.DataBase;
import com.gitee.hengboy.builder.core.database.impl.*;
import lombok.Getter;

/**
 * 数据库类型枚举
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：4:35 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Getter
public enum DbTypeEnum {
    /**
     * MySQL数据库类型
     */
    MySQL("com.mysql.jdbc.Driver", MySqlDataBase.class),
    /**
     * Db2数据库类型
     */
    Db2("com.ibm.db2.jcc.DB2Driver", Db2DataBase.class),
    /**
     * PostgreSQL数据库类型
     */
    PostgreSQL("org.postgresql.Driver", PostgreSqlDataBase.class),
    /**
     * SQLServer数据库类型
     */
    SQLServer("com.microsoft.sqlserver.jdbc.SQLServerDriver", SqlServerDataBase.class),
    /**
     * Oracle数据库类型
     */
    Oracle("oracle.jdbc.driver.OracleDriver", OracleDataBase.class);
    /**
     * 驱动类全限定名
     */
    private String value;
    /**
     * 数据库实现类位置
     */
    private Class<? extends DataBase> dataBaseImplClass;

    DbTypeEnum(String value, Class<? extends DataBase> dataBaseImplClass) {
        this.value = value;
        this.dataBaseImplClass = dataBaseImplClass;
    }
}
