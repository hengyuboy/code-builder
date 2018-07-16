
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
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC字段类型装载器
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：6:23 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class JdbcTypeResolver {

    private static Map<Integer, String> typeToName;
    private static Map<String, Integer> nameToType;
    private static final String TYPE_ARRAY = "ARRAY";
    private static final String TYPE_BIGINT = "BIGINT";
    private static final String TYPE_BINARY = "BINARY";
    private static final String TYPE_BIT = "BIT";
    private static final String TYPE_BLOB = "BLOB";
    private static final String TYPE_BOOLEAN = "BOOLEAN";
    private static final String TYPE_CHAR = "CHAR";
    private static final String TYPE_CLOB = "CLOB";
    private static final String TYPE_DATALINK = "DATALINK";
    private static final String TYPE_DATE = "DATE";
    private static final String TYPE_DECIMAL = "DECIMAL";
    private static final String TYPE_DISTINCT = "DISTINCT";
    private static final String TYPE_DOUBLE = "DOUBLE";
    private static final String TYPE_FLOAT = "FLOAT";
    private static final String TYPE_INTEGER = "INTEGER";
    private static final String TYPE_JAVA_OBJECT = "JAVA_OBJECT";
    private static final String TYPE_LONGVARBINARY = "LONGVARBINARY";
    private static final String TYPE_LONGVARCHAR = "LONGVARCHAR";
    private static final String TYPE_NCHAR = "NCHAR";
    private static final String TYPE_NCLOB = "NCLOB";
    private static final String TYPE_NVARCHAR = "NVARCHAR";
    private static final String TYPE_LONGNVARCHAR = "LONGNVARCHAR";
    private static final String TYPE_NULL = "NULL";
    private static final String TYPE_NUMERIC = "NUMERIC";
    private static final String TYPE_OTHER = "OTHER";
    private static final String TYPE_REAL = "REAL";
    private static final String TYPE_REF = "REF";
    private static final String TYPE_SMALLINT = "SMALLINT";
    private static final String TYPE_STRUCT = "STRUCT";
    private static final String TYPE_TIME = "TIME";
    private static final String TYPE_TIMESTAMP = "TIMESTAMP";
    private static final String TYPE_TINYINT = "TINYINT";
    private static final String TYPE_VARBINARY = "VARBINARY";
    private static final String TYPE_VARCHAR = "VARCHAR";

    static {
        typeToName = new HashMap<Integer, String>();
        typeToName.put(Types.ARRAY, TYPE_ARRAY);
        typeToName.put(Types.BIGINT, TYPE_BIGINT);
        typeToName.put(Types.BINARY, TYPE_BINARY);
        typeToName.put(Types.BIT, TYPE_BIT);
        typeToName.put(Types.BLOB, TYPE_BLOB);
        typeToName.put(Types.BOOLEAN, TYPE_BOOLEAN);
        typeToName.put(Types.CHAR, TYPE_CHAR);
        typeToName.put(Types.CLOB, TYPE_CLOB);
        typeToName.put(Types.DATALINK, TYPE_DATALINK);
        typeToName.put(Types.DATE, TYPE_DATE);
        typeToName.put(Types.DECIMAL, TYPE_DECIMAL);
        typeToName.put(Types.DISTINCT, TYPE_DISTINCT);
        typeToName.put(Types.DOUBLE, TYPE_DOUBLE);
        typeToName.put(Types.FLOAT, TYPE_FLOAT);
        typeToName.put(Types.INTEGER, TYPE_INTEGER);
        typeToName.put(Types.JAVA_OBJECT, TYPE_JAVA_OBJECT);
        typeToName.put(Types.LONGVARBINARY, TYPE_LONGVARBINARY);
        typeToName.put(Types.LONGVARCHAR, TYPE_LONGVARCHAR);
        typeToName.put(Jdbc4Types.NCHAR, TYPE_NCHAR);
        typeToName.put(Jdbc4Types.NCLOB, TYPE_NCLOB);
        typeToName.put(Jdbc4Types.NVARCHAR, TYPE_NVARCHAR);
        typeToName.put(Jdbc4Types.LONGNVARCHAR, TYPE_LONGNVARCHAR);
        typeToName.put(Types.NULL, TYPE_NULL);
        typeToName.put(Types.NUMERIC, TYPE_NUMERIC);
        typeToName.put(Types.OTHER, TYPE_OTHER);
        typeToName.put(Types.REAL, TYPE_REAL);
        typeToName.put(Types.REF, TYPE_REF);
        typeToName.put(Types.SMALLINT, TYPE_SMALLINT);
        typeToName.put(Types.STRUCT, TYPE_STRUCT);
        typeToName.put(Types.TIME, TYPE_TIME);
        typeToName.put(Types.TIMESTAMP, TYPE_TIMESTAMP);
        typeToName.put(Types.TINYINT, TYPE_TINYINT);
        typeToName.put(Types.VARBINARY, TYPE_VARBINARY);
        typeToName.put(Types.VARCHAR, TYPE_VARCHAR);

        nameToType = new HashMap<String, Integer>();
        nameToType.put(TYPE_ARRAY, Types.ARRAY);
        nameToType.put(TYPE_BIGINT, Types.BIGINT);
        nameToType.put(TYPE_BINARY, Types.BINARY);
        nameToType.put(TYPE_BIT, Types.BIT);
        nameToType.put(TYPE_BLOB, Types.BLOB);
        nameToType.put(TYPE_BOOLEAN, Types.BOOLEAN);
        nameToType.put(TYPE_CHAR, Types.CHAR);
        nameToType.put(TYPE_CLOB, Types.CLOB);
        nameToType.put(TYPE_DATALINK, Types.DATALINK);
        nameToType.put(TYPE_DATE, Types.DATE);
        nameToType.put(TYPE_DECIMAL, Types.DECIMAL);
        nameToType.put(TYPE_DISTINCT, Types.DISTINCT);
        nameToType.put(TYPE_DOUBLE, Types.DOUBLE);
        nameToType.put(TYPE_FLOAT, Types.FLOAT);
        nameToType.put(TYPE_INTEGER, Types.INTEGER);
        nameToType.put(TYPE_JAVA_OBJECT, Types.JAVA_OBJECT);
        nameToType.put(TYPE_LONGVARBINARY, Types.LONGVARBINARY);
        nameToType.put(TYPE_LONGVARCHAR, Types.LONGVARCHAR);
        nameToType.put(TYPE_NCHAR, Jdbc4Types.NCHAR);
        nameToType.put(TYPE_NCLOB, Jdbc4Types.NCLOB);
        nameToType.put(TYPE_NVARCHAR, Jdbc4Types.NVARCHAR);
        nameToType.put(TYPE_LONGNVARCHAR, Jdbc4Types.LONGNVARCHAR);
        nameToType.put(TYPE_NULL, Types.NULL);
        nameToType.put(TYPE_NUMERIC, Types.NUMERIC);
        nameToType.put(TYPE_OTHER, Types.OTHER);
        nameToType.put(TYPE_REAL, Types.REAL);
        nameToType.put(TYPE_REF, Types.REF);
        nameToType.put(TYPE_SMALLINT, Types.SMALLINT);
        nameToType.put(TYPE_STRUCT, Types.STRUCT);
        nameToType.put(TYPE_TIME, Types.TIME);
        nameToType.put(TYPE_TIMESTAMP, Types.TIMESTAMP);
        nameToType.put(TYPE_TINYINT, Types.TINYINT);
        nameToType.put(TYPE_VARBINARY, Types.VARBINARY);
        nameToType.put(TYPE_VARCHAR, Types.VARCHAR);
    }

    private JdbcTypeResolver() {
        super();
    }

    public static String getJdbcTypeName(int jdbcType) {
        String answer = typeToName.get(jdbcType);
        if (answer == null) {
            answer = TYPE_OTHER;
        }

        return answer;
    }

    public static int getJdbcType(String jdbcTypeName) {
        Integer answer = nameToType.get(jdbcTypeName);
        if (answer == null) {
            answer = Types.OTHER;
        }

        return answer;
    }
}
