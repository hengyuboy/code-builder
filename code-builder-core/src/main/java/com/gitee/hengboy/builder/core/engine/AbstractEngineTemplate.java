package com.gitee.hengboy.builder.core.engine;
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
import com.gitee.hengboy.builder.common.util.StringUtil;
import com.gitee.hengboy.builder.core.configuration.BuilderConfiguration;
import com.gitee.hengboy.builder.core.configuration.TemplateConfiguration;
import com.gitee.hengboy.builder.core.database.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * 驱动抽象类
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/12
 * Time：2:22 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public abstract class AbstractEngineTemplate implements EngineTemplate {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(AbstractEngineTemplate.class);

    /**
     * 数据库对象实例
     */
    protected DataBase dataBase;
    /**
     * 自动生成参数实体实例
     */
    protected CodeBuilderProperties codeBuilderProperties;
    /**
     * 系统分隔符
     */
    protected static final String SEPARATOR = File.separator;
    /**
     * 自动生成文件的后缀名
     */
    protected static final String FILE_SUFFIX = ".java";
    /**
     * package 分隔符
     */
    protected static final String PACKAGE_SPLIT = ".";

    /**
     * 构造函数初始化数据库对象实例、代码生成配置实体
     *
     * @param dataBase              数据库对象实例
     * @param codeBuilderProperties 代码生成配置实体
     */
    public AbstractEngineTemplate(DataBase dataBase, CodeBuilderProperties codeBuilderProperties) {
        this.dataBase = dataBase;
        this.codeBuilderProperties = codeBuilderProperties;
    }

    /**
     * 执行输出日志
     * 打印本次执行的基本信息
     *
     * @param tablesSize 本次生成表格的数量
     */
    public void invokeConsoleLog(int tablesSize) {
        logger.info("Code-Builder >>> 本次有{}个表参与生成.", tablesSize);
        logger.info("Code-Builder >>> 执行项目目录：{}", getProjectDir());
        logger.info("Code-Builder >>> 生成目录：{}", getBasePackageTargetDir());
        logger.info("Code-Builder >>> Builder根目录：{}", getBaseBuilderDir());
    }

    /**
     * 循环生成文件
     *
     * @param tableNames 数据表列表
     */
    public void loopProcess(List<String> tableNames) {
        for (String tableName : tableNames) {
            logger.info("Auto Builder Table > 【{}】", tableName);
            process(dataBase.getTable(tableName));
        }
        // 执行生成日志输出
        invokeConsoleLog(tableNames.size());
    }

    /**
     * 获取自动化配置对象实例
     * 从builder.yml配置文件内自动映射为配置类实例
     *
     * @return 配置对象实例
     */
    protected BuilderConfiguration getConfiguration() {
        return codeBuilderProperties.getBuilder();
    }

    /**
     * 获取项目目录
     *
     * @return 项目根路径
     */
    protected String getProjectDir() {
        return codeBuilderProperties.getProjectBaseDir() + SEPARATOR;
    }

    /**
     * 获取生成目标目录地址
     *
     * @return 目标根路径
     */
    protected String getBaseTargetDir() {
        StringBuffer baseTargetDir = new StringBuffer();
        // 项目根地址
        baseTargetDir.append(getProjectDir());
        // 生成目标目录
        baseTargetDir.append(codeBuilderProperties.getTargetDir());
        baseTargetDir.append(SEPARATOR);
        return baseTargetDir.toString();
    }

    /**
     * 获取builder基础目录地址
     *
     * @return builder根路径
     */
    protected String getBaseBuilderDir() {
        StringBuffer builderDir = new StringBuffer();
        // 项目根地址
        builderDir.append(codeBuilderProperties.getProjectBaseDir());
        // 系统分隔符
        builderDir.append(SEPARATOR);

        // builder根地址
        builderDir.append(codeBuilderProperties.getBuilderDir());
        // 系统分隔符
        builderDir.append(SEPARATOR);
        return builderDir.toString();
    }

    /**
     * 获取模板创建文件后的包名
     *
     * @param templateConfiguration 模板配置对象
     * @return 包名
     */
    protected String getTemplatePackageName(TemplateConfiguration templateConfiguration) {
        // 包名前缀
        StringBuffer packageName = new StringBuffer(codeBuilderProperties.getBuilder().getPackagePrefix());
        // 默认包名
        if (StringUtil.isNotEmpty(templateConfiguration.getPackageName())) {
            packageName.append(PACKAGE_SPLIT);
            // 转换为小写
            packageName.append(templateConfiguration.getPackageName().toLowerCase());
        }
        return packageName.toString();
    }

    /**
     * 获取生成目标目录 + package目录格式化后的根地址
     *
     * @return 存在包名的目录根路径
     */
    protected String getBasePackageTargetDir() {
        // 获取builder配置信息
        BuilderConfiguration builderConfiguration = codeBuilderProperties.getBuilder();
        StringBuffer basePackageDir = new StringBuffer();
        // 生成文件的目标根路径
        basePackageDir.append(getBaseTargetDir());

        // 是否存在自定义的package前缀
        // 存在前缀添加到路径内
        if (StringUtil.isNotEmpty(builderConfiguration.getDiffSysPackagePrefix())) {
            basePackageDir.append(builderConfiguration.getDiffSysPackagePrefix());
        }
        // 系统分隔符
        basePackageDir.append(SEPARATOR);
        return basePackageDir.toString();
    }

    /**
     * 获取模板生成文件后的类名
     *
     * @param templateConfiguration 默认配置信息
     * @param entityName            数据表对应的实体名称
     * @return Class 名称
     */
    protected String getTemplateClassName(TemplateConfiguration templateConfiguration, String entityName) {
        StringBuffer className = new StringBuffer();
        // 追加文件前缀名
        if (StringUtil.isNotEmpty(templateConfiguration.getFilePrefix())) {
            className.append(StringUtil.getCamelCaseString(templateConfiguration.getFilePrefix(), true));
        }
        // 实体类名称
        className.append(entityName);
        // 追加文件后缀名
        if (StringUtil.isNotEmpty(templateConfiguration.getFileSuffix())) {
            className.append(StringUtil.getCamelCaseString(templateConfiguration.getFileSuffix(), true));
        }
        return className.toString();
    }

    /**
     * 获取新文件的全名称
     * 如：XxxEntity.java
     *
     * @param templateConfiguration 模板配置对象
     * @param entityName            数据表对应的实体名称
     * @return 新文件名称
     */
    protected String getTemplateNewFileName(TemplateConfiguration templateConfiguration, String entityName) {
        StringBuffer fileName = new StringBuffer();
        // 目标package的根目录
        fileName.append(getBasePackageTargetDir());

        // 是否配置了模板创建文件后所属的package目录
        if (StringUtil.isNotEmpty(templateConfiguration.getPackageName())) {
            fileName.append(templateConfiguration.getPackageName());
            fileName.append(SEPARATOR);
        }
        // 类名
        fileName.append(getTemplateClassName(templateConfiguration, entityName));
        // 文件扩展名
        fileName.append(FILE_SUFFIX);
        return fileName.toString();
    }

    /**
     * 循环创建package
     * 根据builder.yml配置文件内的参数packagePrefix进行自动创建package包名的文件夹
     * 如：com.code.builder
     * 则自动创建com/code/builder文件夹
     *
     * @param templateConfiguration 模板配置
     */
    protected void loopCreatePackage(TemplateConfiguration templateConfiguration) {
        // 目录地址
        StringBuffer basePackagePath = new StringBuffer();
        // 目标根地址
        basePackagePath.append(getBasePackageTargetDir());
        // 模板生成文件目标独有的package
        if (StringUtil.isNotEmpty(templateConfiguration.getPackageName())) {
            basePackagePath.append(templateConfiguration.getPackageName());
        }
        // 执行创建目录
        File basePackage = new File(basePackagePath.toString());
        basePackage.mkdirs();
    }
}
