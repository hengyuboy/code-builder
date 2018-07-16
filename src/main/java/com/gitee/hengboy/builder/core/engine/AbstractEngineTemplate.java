package com.gitee.hengboy.builder.core.engine;

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.StringUtil;
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
     * 循环生成文件
     *
     * @param tableNames 数据表列表
     */
    public void loopProcess(List<String> tableNames) {
        for (String tableName : tableNames) {
            logger.info("Auto Builder Table > 【{}】", tableName);
            process(dataBase.getTable(tableName));
        }
        logger.info("This Time builder {} tables", tableNames.size());
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
     * 获取生成目标目录 + package目录格式化后的根地址
     *
     * @param builderConfiguration 代理生成配置信息
     * @return 存在包名的目录根路径
     */
    protected String getBasePackageTargetDir(BuilderConfiguration builderConfiguration) {
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
     * 获取新文件的全名称
     * 如：XxxEntity.java
     *
     * @param builderConfiguration  配置对象
     * @param templateConfiguration 模板配置对象
     * @param className             类名
     * @return 新文件名称
     */
    protected String getNewFileName(BuilderConfiguration builderConfiguration, TemplateConfiguration templateConfiguration, String className) {
        StringBuffer fileName = new StringBuffer();
        // 目标package的根目录
        fileName.append(getBasePackageTargetDir(builderConfiguration));

        // 是否配置了模板创建文件后所属的package目录
        if (StringUtil.isNotEmpty(templateConfiguration.getPackageName())) {
            fileName.append(templateConfiguration.getPackageName());
            fileName.append(SEPARATOR);
        }
        // 文件名
        fileName.append(className);
        // 是否添加文件的后缀
        if (StringUtil.isNotEmpty(templateConfiguration.getFileSuffix())) {
            fileName.append(templateConfiguration.getFileSuffix());
        }
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
     * @param builderConfiguration  代码生成配置信息
     * @param templateConfiguration 模板配置
     */
    protected void loopCreatePackage(BuilderConfiguration builderConfiguration, TemplateConfiguration templateConfiguration) {
        // 目录地址
        StringBuffer basePackagePath = new StringBuffer();
        basePackagePath.append(getBasePackageTargetDir(builderConfiguration));
        // 模板生成文件目标独有的package
        if (StringUtil.isNotEmpty(templateConfiguration.getPackageName())) {
            basePackagePath.append(templateConfiguration.getPackageName());
        }
        // 执行创建目录
        File basePackage = new File(basePackagePath.toString());
        basePackage.mkdirs();
    }
}
