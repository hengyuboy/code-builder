package com.gitee.hengboy.builder.spring.boot.autoconfigure;

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.core.configuration.BuilderConfiguration;
import com.gitee.hengboy.builder.core.configuration.TemplateConfiguration;
import com.gitee.hengboy.builder.core.invoke.CodeBuilderInvoke;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

/**
 * 代码生成器自动化配置
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/19
 * Time：11:37 AM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Configuration
@ConditionalOnClass({BuilderConfiguration.class, TemplateConfiguration.class})
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(BuilderAutoConfigureProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class CodeBuilderAutoConfiguration {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(CodeBuilderAutoConfiguration.class);

    private BuilderAutoConfigureProperties builderAutoConfigureProperties;
    private DataSource dataSource;

    /**
     * 初始化BuilderAutoConfigureProperties配置类
     *
     * @param builderAutoConfigureProperties 代码生成器配置类实例
     * @param dataSource                     数据源
     */
    public CodeBuilderAutoConfiguration(BuilderAutoConfigureProperties builderAutoConfigureProperties,
                                        ObjectProvider<DataSource> dataSource) {
        this.builderAutoConfigureProperties = builderAutoConfigureProperties;
        this.dataSource = dataSource.getIfAvailable();
        invokeBuilder();
    }

    /**
     * Invoke Code Builder
     */
    void invokeBuilder() {
        try {
            CodeBuilderInvoke.invoke(
                    CodeBuilderProperties.builder()
                            .execute(builderAutoConfigureProperties.isExecute())
                            .dataSource(dataSource)
                            .dbType(builderAutoConfigureProperties.getDbType())
                            .tables(builderAutoConfigureProperties.getTables())
                            .generatorByPattern(builderAutoConfigureProperties.getGeneratorByPattern())
                            .ignoreClassPrefix(builderAutoConfigureProperties.getIgnoreClassPrefix())
                            // 项目根路径
                            .projectBaseDir(ResourceUtils.getURL(CLASSPATH_URL_PREFIX).getPath().replace("/target/classes/", ""))
                            .builderDir(builderAutoConfigureProperties.getBuilderDir())
                            .targetDir(builderAutoConfigureProperties.getTargetDir())
                            .builder(builderAutoConfigureProperties.getConfiguration())
                            .engineTypeEnum(builderAutoConfigureProperties.getEngineTypeEnum())
                            .build());
        } catch (Exception e) {
            logger.error("InvokeBuilder have error ：{}", e.getMessage());
        }

    }
}
