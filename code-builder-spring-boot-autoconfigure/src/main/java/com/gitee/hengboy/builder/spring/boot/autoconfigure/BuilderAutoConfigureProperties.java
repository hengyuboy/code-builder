package com.gitee.hengboy.builder.spring.boot.autoconfigure;

import com.gitee.hengboy.builder.common.enums.DbTypeEnum;
import com.gitee.hengboy.builder.common.enums.EngineTypeEnum;
import com.gitee.hengboy.builder.core.configuration.BuilderConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static com.gitee.hengboy.builder.spring.boot.autoconfigure.BuilderAutoConfigureProperties.CODE_BUILDER_PREFIX;

/**
 * 代码生成自动配置文件
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/19
 * Time：11:40 AM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
@ConfigurationProperties(prefix = CODE_BUILDER_PREFIX)
public class BuilderAutoConfigureProperties {

    /**
     * Properties Prefix
     */
    public static final String CODE_BUILDER_PREFIX = "hengboy.code.builder";

    private boolean execute;
    private String ignoreClassPrefix;
    private List<String> tables;
    private String generatorByPattern;
    private DbTypeEnum dbType = DbTypeEnum.MySQL;
    private EngineTypeEnum engineTypeEnum = EngineTypeEnum.FREEMARKER;
    private String projectBaseDir;
    private String builderDir = "target.classes.templates.builder";
    private String targetDir = "target.generated-sources.java";
    private BuilderConfiguration configuration;
}
