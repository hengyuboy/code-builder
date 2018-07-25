package com.gitee.hengboy.builder;
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
import com.gitee.hengboy.builder.common.enums.DbTypeEnum;
import com.gitee.hengboy.builder.common.enums.EngineTypeEnum;
import com.gitee.hengboy.builder.core.configuration.BuilderConfiguration;
import com.gitee.hengboy.builder.core.invoke.CodeBuilderInvoke;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

/**
 * 代码生成器Maven插件入口类
 * <p>简书：http://www.jianshu.com/u/092df3f77bca</p>
 * <p>码云：https://gitee.com/hengboy</p>
 *
 * @author 恒宇少年
 */
@Mojo(name = "generator", defaultPhase = LifecyclePhase.COMPILE)
@Execute(phase = LifecyclePhase.COMPILE)
public class CodeBuilderMojo
        extends AbstractMojo {
    /**
     * 是否执行自动生成
     * 默认值：不执行
     */
    @Parameter(defaultValue = "false", required = true)
    private boolean execute;
    /**
     * 数据库类型
     * 自动根据类型加载对应的驱动
     * 如：MySQL => com.mysql.jdbc.Driver
     */
    @Parameter(defaultValue = "MySQL")
    private DbTypeEnum dbType;
    /**
     * 数据库名称
     */
    @Parameter(required = true)
    private String dbName;
    /**
     * 数据库连接地址
     * 排除数据库名称
     * 如：jdbc:mysql://xxx.xx.xx.xxx:3306
     */
    @Parameter(required = true)
    private String dbUrl;
    /**
     * 数据库连接用户名
     */
    @Parameter(required = true)
    private String dbUserName;
    /**
     * 数据库连接密码
     */
    @Parameter
    private String dbPassword;
    /**
     * 数据库驱动类全局限定名
     */
    @Parameter
    private String dbDriverClassName;
    /**
     * 忽略表名的前缀
     * 如建表时表名为：sys_menu_info
     * 配置忽略前缀为：sys_
     * 生成对应的Class后为：MenuInfo
     */
    @Parameter
    private String ignoreClassPrefix;

    /**
     * 指定生成的表列表
     * 根据指定的表名进行生成
     * 如：
     * <tables>
     * <table>sys_menu_info</table>
     * <table>sys_role_info</table>
     * </tables>
     * 会自动生成sys_menu_info、sys_role_info两张表对应的模板文件
     */
    @Parameter
    private List<String> tables;
    /**
     * 根据指定前缀生成
     * 如：sys_
     * 会匹配：sys_menu_info、sys_role_info、sys_button_info等表
     */
    @Parameter
    private String generatorByPattern;
    /**
     * 驱动模板类型
     * 默认值：freemarker
     */
    @Parameter(defaultValue = "FREEMARKER")
    private EngineTypeEnum engineType;

    /**
     * 项目根地址
     */
    @Parameter(defaultValue = "${basedir}")
    private String projectBaseDir;

    /**
     * builder配置文件目录地址
     * 这里配置分隔符是"."
     * 因为linux、osx、windows下的分隔符不一样
     * 需要根据java获取系统的分隔符后格式化
     */
    @Parameter(defaultValue = "target.classes.templates.builder")
    private String builderDir;

    /**
     * 文件生成后目标根地址
     */
    @Parameter(defaultValue = "target.generated-sources.java")
    private String targetDir;
    /**
     * 自动生成配置信息实体
     */
    @Parameter
    private BuilderConfiguration builder;

    /**
     * 执行插件入口方法
     * - 构造自动构建参数对象
     * - 获取数据库对象
     * - 获取驱动模型
     * - 执行驱动生成文件
     *
     * @throws MojoExecutionException mojo执行异常
     * @throws MojoFailureException   mojo错误异常
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            /*
             * 组装代码生成器所需的实体参数
             * 传递到所需配置类
             */
            CodeBuilderProperties codeBuilderProperties = CodeBuilderProperties.builder()
                    .execute(execute)
                    .dbType(dbType)
                    .dbName(dbName)
                    .dbUserName(dbUserName)
                    .dbPassword(dbPassword)
                    .dbUrl(dbUrl)
                    .dbDriverClassName(dbDriverClassName)
                    .tables(tables)
                    .generatorByPattern(generatorByPattern)
                    .ignoreClassPrefix(ignoreClassPrefix)
                    .projectBaseDir(projectBaseDir)
                    .builderDir(builderDir)
                    .targetDir(targetDir)
                    .builder(builder)
                    .engineTypeEnum(engineType)
                    .build();

            // 执行代码生成
            CodeBuilderInvoke.invoke(codeBuilderProperties);

        } catch (Exception e) {
            getLog().error("Invoke have errors ：" + e.getMessage());
        }

    }
}
