package com.gitee.hengboy.builder.core.database.model.util;
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
import com.gitee.hengboy.builder.common.enums.JavaTypeEnum;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Java数据类型装载器
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：6:13 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class JavaTypeResolver {

    /**
     * java 字段类型映射集合
     */
    private static Map<Integer, JavaTypeEnum> typeMap;


    static {
        typeMap = new HashMap<Integer, JavaTypeEnum>();
        typeMap.put(Types.ARRAY, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.BIGINT, JavaTypeEnum.TYPE_LONG);
        typeMap.put(Types.BINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        typeMap.put(Types.BIT, JavaTypeEnum.TYPE_BOOLEAN);
        typeMap.put(Types.BLOB, JavaTypeEnum.TYPE_BYTE_ARRAY);
        typeMap.put(Types.BOOLEAN, JavaTypeEnum.TYPE_BOOLEAN);
        typeMap.put(Types.CHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.CLOB, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.DATALINK, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.DATE, JavaTypeEnum.TYPE_DATE);
        typeMap.put(Types.DISTINCT, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.DOUBLE, JavaTypeEnum.TYPE_DOUBLE);
        typeMap.put(Types.FLOAT, JavaTypeEnum.TYPE_DOUBLE);
        typeMap.put(Types.INTEGER, JavaTypeEnum.TYPE_INTEGER);
        typeMap.put(Types.JAVA_OBJECT, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Jdbc4Types.LONGNVARCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.LONGVARBINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        typeMap.put(Types.LONGVARCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Jdbc4Types.NCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Jdbc4Types.NCLOB, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Jdbc4Types.NVARCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.NULL, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.OTHER, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.REAL, JavaTypeEnum.TYPE_FLOAT);
        typeMap.put(Types.REF, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.SMALLINT, JavaTypeEnum.TYPE_SHORT);
        typeMap.put(Types.STRUCT, JavaTypeEnum.TYPE_OBJECT);
        typeMap.put(Types.TIME, JavaTypeEnum.TYPE_DATE);
        typeMap.put(Types.TIMESTAMP, JavaTypeEnum.TYPE_TIMESTAMP);
        typeMap.put(Types.TINYINT, JavaTypeEnum.TYPE_BYTE);
        typeMap.put(Types.VARBINARY, JavaTypeEnum.TYPE_BYTE_ARRAY);
        typeMap.put(Types.VARCHAR, JavaTypeEnum.TYPE_STRING);
        typeMap.put(Types.DECIMAL, JavaTypeEnum.TYPE_BIG_DECIMAL);
        typeMap.put(Types.NUMERIC, JavaTypeEnum.TYPE_BIG_DECIMAL);
    }

    /**
     * 获取java数据类型
     *
     * @param jdbcType       数据库类型
     * @param isFullJavaType 是否获取java类型的全路径
     * @return java数据类型全路径
     */
    public static String getJavaType(int jdbcType, boolean isFullJavaType) {
        return isFullJavaType ? typeMap.get(jdbcType).getFullName() : typeMap.get(jdbcType).getShortName();
    }
}
