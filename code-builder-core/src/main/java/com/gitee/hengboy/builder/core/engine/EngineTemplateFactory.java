package com.gitee.hengboy.builder.core.engine;
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
import com.gitee.hengboy.builder.common.enums.EngineTypeEnum;
import com.gitee.hengboy.builder.common.enums.ErrorEnum;
import com.gitee.hengboy.builder.common.exception.CodeBuilderException;
import com.gitee.hengboy.builder.core.database.DataBase;

import java.lang.reflect.Constructor;

/**
 * 驱动模板动态工厂
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/11
 * Time：5:55 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class EngineTemplateFactory {
    private EngineTemplateFactory() {
    }

    /**
     * 根据驱动枚举动态初始化获取驱动实现类实例
     *
     * @param engineTypeEnum        驱动枚举
     * @param codeBuilderProperties 配置参数实体
     * @param dataBase              数据库连接实例
     * @return 驱动模板实例
     */
    public static EngineTemplate newInstance(EngineTypeEnum engineTypeEnum, DataBase dataBase, CodeBuilderProperties codeBuilderProperties) {
        try {
            // 获取数据库实现类的构造函数
            Constructor constructor = engineTypeEnum.getImplClass().getConstructor(DataBase.class, CodeBuilderProperties.class);
            // 反射获取数据库实现类实例
            return (EngineTemplate) constructor.newInstance(dataBase, codeBuilderProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new CodeBuilderException(ErrorEnum.NOT_ALLOW_ENGINE);
    }
}
