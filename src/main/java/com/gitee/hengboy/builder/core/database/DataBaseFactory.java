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

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.enums.DbTypeEnum;
import com.gitee.hengboy.builder.common.enums.ErrorEnum;
import com.gitee.hengboy.builder.common.exception.CodeBuilderException;

import java.lang.reflect.Constructor;

/**
 * 数据库工厂
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:22 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class DataBaseFactory {
    /**
     * 构造函数私有化
     * 禁止通过new方式实例化对象
     */
    private DataBaseFactory() {
    }

    /**
     * 获取配置的数据库类型实例
     *
     * @param codeBuilderProperties 配置构建参数实体
     * @return 数据库实例
     */
    public static DataBase newInstance(CodeBuilderProperties codeBuilderProperties) {
        // 数据库类型枚举实例
        DbTypeEnum dbTypeEnum = codeBuilderProperties.getDbType();
        try {
            // 获取数据库实现类的构造函数
            Constructor constructor = dbTypeEnum.getDataBaseImplClass().getConstructor(CodeBuilderProperties.class);
            // 反射获取数据库实现类实例
            return (DataBase) constructor.newInstance(codeBuilderProperties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CodeBuilderException(ErrorEnum.NOT_ALLOW_DB_TYPE);
        }
    }
}
