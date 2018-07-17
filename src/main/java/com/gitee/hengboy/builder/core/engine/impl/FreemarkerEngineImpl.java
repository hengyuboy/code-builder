package com.gitee.hengboy.builder.core.engine.impl;
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
import com.gitee.hengboy.builder.common.enums.EngineTypeEnum;
import com.gitee.hengboy.builder.core.configuration.BuilderConfiguration;
import com.gitee.hengboy.builder.core.configuration.TemplateConfiguration;
import com.gitee.hengboy.builder.core.database.DataBase;
import com.gitee.hengboy.builder.core.database.model.Table;
import com.gitee.hengboy.builder.core.engine.AbstractEngineTemplate;
import com.gitee.hengboy.builder.core.engine.model.DataModelEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Locale;

/**
 * 使用freemarker模板驱动实现类
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:20 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class FreemarkerEngineImpl extends AbstractEngineTemplate {
    /**
     * freemarker配置对象实例化
     * 采用2.3.28版本
     */
    private Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

    /**
     * 默认字符集
     */
    static final String DEFAULT_ENCODING = "UTF-8";
    /**
     * 默认国际化
     */
    static final String DEFAULT_LOCALE = "zh_CN";

    public FreemarkerEngineImpl(DataBase dataBase, CodeBuilderProperties codeBuilderProperties) {
        super(dataBase, codeBuilderProperties);
    }

    /**
     * 设置freemarker全局配置参数
     */
    {
        try {
            configuration.setDirectoryForTemplateLoading(new File(getBaseBuilderDir() + EngineTypeEnum.FREEMARKER.getTemplateDirName()));
            configuration.setDefaultEncoding(DEFAULT_ENCODING);
            configuration.setLocale(new Locale(DEFAULT_LOCALE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建指定表的实体
     *
     * @param table 数据表对象
     */
    public void process(Table table) {
        try {
            // 获取配置
            BuilderConfiguration builderConfiguration = getConfiguration();
            // 遍历生成文件
            for (TemplateConfiguration templateConfiguration : builderConfiguration.getTemplates()) {
                // 创建package
                loopCreatePackage(templateConfiguration);
                // freemarker模板
                Template template = configuration.getTemplate(templateConfiguration.getName());
                // 创建文件
                File file = new File(getTemplateNewFileName(templateConfiguration, table.getEntityName()));
                // 写入freemarker模板内容
                Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), DEFAULT_ENCODING));

                /*
                 * 构建数据模型实体
                 * 1. 设置当前模板创建类的包名
                 * 2. 设置当前模板创建类的类名
                 * 3. 设置数据表格对象
                 */
                DataModelEntity dataModelEntity = DataModelEntity.builder()
                        .packageName(getTemplatePackageName(templateConfiguration))
                        .className(getTemplateClassName(templateConfiguration, table.getEntityName()))
                        .table(table)
                        .build();

                // 执行生成
                template.process(dataModelEntity, out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
