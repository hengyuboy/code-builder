package com.gitee.hengboy.builder.core.database;

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.DbTypeEnum;
import com.gitee.hengboy.builder.common.ErrorEnum;

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
            throw new RuntimeException(ErrorEnum.NOT_ALLOW_DB_TYPE.getMessage());
        }
    }
}
