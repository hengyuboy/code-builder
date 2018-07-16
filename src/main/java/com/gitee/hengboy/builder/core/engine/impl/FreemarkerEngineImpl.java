package com.gitee.hengboy.builder.core.engine.impl;

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.EngineTypeEnum;
import com.gitee.hengboy.builder.core.configuration.BuilderConfiguration;
import com.gitee.hengboy.builder.core.configuration.TemplateConfiguration;
import com.gitee.hengboy.builder.core.database.DataBase;
import com.gitee.hengboy.builder.core.database.model.Table;
import com.gitee.hengboy.builder.core.engine.AbstractEngineTemplate;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Locale;

/**
 * 使用freemarker模板驱动实现类
 *
 * @author：于起宇
 * ===============================
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
     */ {
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
                loopCreatePackage(builderConfiguration, templateConfiguration);
                // freemarker模板
                Template template = configuration.getTemplate(templateConfiguration.getName());
                // 创建文件
                File file = new File(getNewFileName(builderConfiguration, templateConfiguration, table.getClassName()));
                // 写入freemarker模板内容
                Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), DEFAULT_ENCODING));
                // 执行生成
                template.process(table, out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
