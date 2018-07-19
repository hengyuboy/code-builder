package com.gitee.hengboy.builder.common;
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
import com.gitee.hengboy.builder.common.enums.DbTypeEnum;
import com.gitee.hengboy.builder.common.enums.EngineTypeEnum;
import com.gitee.hengboy.builder.core.configuration.BuilderConfiguration;
import lombok.Builder;
import lombok.Data;

import javax.sql.DataSource;
import java.io.File;
import java.util.List;

/**
 * 代码生成器所属参数实体
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：4:58 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
@Builder
public class CodeBuilderProperties {
    private boolean execute;

    /**
     * maven-plugin形式所需参数
     * 数据库配置信息
     */
    private String dbName;
    private String dbUserName;
    private String dbPassword;
    private String dbUrl;
    private String dbDriverClassName;
    /**
     * 数据源
     * spring-boot-starter形式直接配置
     */
    private DataSource dataSource;

    private String ignoreClassPrefix;
    private List<String> tables;
    private String generatorByPattern;
    private DbTypeEnum dbType;
    private EngineTypeEnum engineTypeEnum;
    private String projectBaseDir;
    private String builderDir;
    private String targetDir;
    private BuilderConfiguration builder;

    /**
     * 获取格式化后的数据库连接字符串
     *
     * @return 格式化后的数据库连接字符串
     */
    public String getDbUrl() {
        return dbUrl + "/" + dbName;
    }

    /**
     * 根据不同系统类型格式化配置文件目录
     * 默认值未格式化前：classes.templates.builder
     * windows系统默认值格式化后：classes\templates\builder
     * osx/linux系统默认值格式化后：classes/templates/builder
     *
     * @return 格式化后的builder根路径
     */
    public String getBuilderDir() {
        return builderDir.replace(".", File.separator);
    }

    /**
     * 根据不同系统类型格式化生成后目标文件目录
     * 默认值未格式化前：generated-sources.java
     * windows系统默认值格式化后：generated-sources\java
     * osx/linux系统默认值格式化后：generated-sources/java
     *
     * @return 格式化后的目录文件跟路径
     */
    public String getTargetDir() {
        return targetDir.replace(".", File.separator);
    }
}
