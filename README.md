## 欢迎使用代码生成器Code-Builder

#### 更新记录

- 1.0.5.RELEASE
   - 添加模板前缀参数`filePrefix`
   - 修改项目根地址排除`target`
   - 生成完成后输出本次执行信息


#### Maven Center 版本
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.gitee.hengboy/code-builder-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.gitee.hengboy/code-builder-spring-boot-starter)

### 欢迎关注公众号

![微信公众号](http://resource.hengboy.com/image/qrcode.jpg)
关注微信公众号，回复`加群`，获取交流群群号。

### 背景
本来`code-builder`是专门为`MyBatis Enhance`来编写的一块代码生成器
### code-builder可以用来做什么？
`code-builder`是一款代码生成`maven mojo`插件，通过简单的配置就可以完成数据库内`Table`转换`Entity`或者其他实体类，想怎么生成完全根据你的个人业务逻辑，`code-builder`尽可能的完善的提供数据库内的一些定义的信息，让你更方便更灵活的来生成`Java`文件。

### 使用环境
- `Maven`构建的项目
- `JDK 1.6`以上版本

### 实现方式

#### 是怎么获取到的数据库信息？
`code-builder`内部采用了`java.sql.Connection`的`MetaData`元数据的方式来获取数据库内`Table`、`Column`等信息，`MetaData`是不局限于任何的数据库类型的，所以`code-builder`在基础设计上是可以在任何数据库类型中来完成它的生成任务的，不过初版本仅支持了`MySQL`、`MariaDB`这两种数据库类型，在`code-builder`后期更新版本中会把主流的数据库进行添加。

#### 生成模板选型
目前`code-builder`内部采用了`freemarker`模板来完成实体类的自动生成，模板由使用者来自定义编写，内部预留了使用其他模板的方式，如果你需要使用别的模板，如：`Velocity`，对应添加生成的实现业务逻辑即可。

### 怎么配置？
#### SpringBoot 方式配置
在`1.0.5.RELEASE`版本添加了集成`SpringBoot`的`starter`，依赖如下所示：
- 使用`Maven`构建工具时，复制下面的内容到`pom.xml`配置文件内
```
<dependency>
    <groupId>com.gitee.hengboy</groupId>
    <artifactId>code-builder-spring-boot-starter</artifactId>
    <version>1.0.5.RELEASE</version>
</dependency>
```
- 如果你是用的`Gradle`构建工具，那么复制下面的内容到你的`build.gradle`
```
compile group: 'com.gitee.hengboy', name: 'code-builder-spring-boot-starter', version: '1.0.5.RELEASE'
```
那么我们在`application.yml`或者`application.properties`配置文件内该怎么配置相关的参数呢？
```
hengboy:
  code:
    builder:
      execute: true
      configuration:
        package-prefix: com.code.builder.sample.codebuildersample
        templates:
          -
            name: entity.ftl
            packageName: model
            fileSuffix: Entity
          -
            name: service.ftl
            packageName: service
            fileSuffix: Service
          -
            name: controller.ftl
            packageName: controller
            fileSuffix: Controller
      generator-by-pattern: '%app_user_info%'
      db-type: mysql
      engine-type-enum: freemarker
      builder-dir: classes.templates.builder
      target-dir: generated-sources.java
      tables:
        - app_shop_type
        - app_user_exchange_good
      ignore-class-prefix: App
```
> 每个参数的具体介绍请往下看。
#### Maven Plugin 方式配置
由于`code-builder`是`Maven mojo`插件的形式创建的，所以我们只需要在项目的`pom.xml`文件内添加`plugin`插件配置，如下所示：
```
<plugin>
    <groupId>com.gitee.hengboy</groupId>
    <artifactId>code-builder-maven-plugin</artifactId>
    <version>1.0.5.RELEASE</version>
    <executions>
        <execution>
            <goals>
                <goal>generator</goal>
            </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>
    </dependencies>
    <configuration>
        <execute>true</execute>
        <dbType>MySQL</dbType>
        <dbDriverClassName>com.mysql.jdbc.Driver</dbDriverClassName>
        <dbName>xxxx</dbName>
        <dbUserName>xxxx</dbUserName>
        <dbPassword>xxxxx</dbPassword>
        <dbUrl>jdbc:mysql://xxx.xx.xx.xx:3306</dbUrl>
        <tables>
             <table>app_shop_type</table>
             <table>app_user_exchange_good</table>
         </tables>
         <engineType>FREEMARKER</engineType>
         <generatorByPattern>%app_user_info%</generatorByPattern>
         <ignoreClassPrefix>App</ignoreClassPrefix>
         <builderDir>classes.templates.builder</builderDir>
         <builder>
                <packagePrefix>com.code.builder.sample</packagePrefix>
		         <templates>
		              <template>
		                  <name>entity.ftl</name>
		                  <packageName>model</packageName>
		              </template>
		              <template>
		                  <name>service.ftl</name>
		                  <packageName>service</packageName>
		                  <fileSuffix>Service</fileSuffix>
		              </template>
		              <template>
		                  <name>mapper.ftl</name>
		                  <packageName>mapper</packageName>
		                  <fileSuffix>Mapper</fileSuffix>
		               </template>
		         </templates>
         </builder>
    </configuration>
</plugin>
```
#### 数据库驱动依赖添加
`code-builder`不局限你使用的数据库类型，所以在生成时需要使用者添加对应数据类型的依赖，如上面的配置中则是添加了`MySQL`数据库的依赖
```
.....
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.46</version>
    </dependency>
</dependencies>
.....
```
#### 生成的控制开关
并不是每一次的`编译`或者`打包`时都需要生成对应的实体，针对这种情况`code-builder`添加了`execute`参数来控制开启与关闭。
- `true`：开启自动生成
- `false`：关闭自动生成

#### 数据库类型配置 
执行自动生成前需要配置数据库的相关配置信息
- `dbType`：数据库类型，默认使用`MySQL`数据库类型。
- `dbDriverClassName`：数据库驱动类名，根据不用的数据库类型配置不同的驱动类名，默认根据`dbType`使用内部定义的类名，如需自定义可以设置。`（仅maven-plugin使用）`

#### 数据库基本信息配置 （仅maven-plugin使用）
- `dbName`：数据库名称
- `dbUserName`：数据库用户名
- `dbPassword`：数据库密码
- `dbUrl`：数据库连接路径，连接路径不需要填写数据库名，正确示例如：`jdbc:mysql://localhost:3306`
#### 生成表名符合规则的表
根据表达式来创建表，表达式与模糊查询语句表达式一般无二，配置`generatorByPattern`参数并设置对应的表达式就可以根据表达式来匹配出参与生成的`Table`列表。
- 指定前缀匹配
```
<generatorByPattern>app_order%</generatorByPattern>
```
示例：将会匹配出`app_order_info`、`app_order_record`等表。
- 指定后缀匹配
```
<generatorByPattern>%order</generatorByPattern>
```
示例：将会匹配出`app_good_order`、`app_exchange_order`等表。
- 包含匹配
```
<generatorByPattern>%order%</generatorByPattern>
```
示例：将会匹配出`app_order_info`、`app_good_order`等表。
#### 生成指定表
`code-builder`支持指定单个或者多个表来生成，只需要配置`tables`参数即可，如下所示：
```
<tables>
    <table>app_shop_type</table>
    <table>app_user_exchange_good</table>
</tables>
```
上面的配置是本次生成仅操作`app_shop_type`、`app_user_exchange_good`两张表。
> 注意：`tables`参数的优先级要高于`generatorByPattern`参数。


#### 自定义builder所需模板路径
`code-builder`会自动去找`classes/templates/builder`下的模板，如果使用默认的`freemarker`模板来生成，那么模板所存放的位置为`classes/templates/builder/freemarker`。
如果你想自定义模板的路径可以设置`builderDir`的地址，在这里因为考虑到了不同操作系统的分隔符不一样（`Windows`系统分隔符`\`，`Linux`以及`O SX`分隔符为`/`）所以这里采用`.`分隔符配置，`code-builder`会自动根据操作系统来转换路径，配置如下所示：
```
<builderDir>classes.code.builder</builderDir>
```

> 注意：`freemarker`文件夹不允许修改，只能修改`code-builder`加载模板的根路径。
#### 排除生成实体后的前缀
数据库设计有时需要添加前缀，如：`app_`、`sys_`等，实际生成实体后前缀则是并不想展示，那么配置参数`ignoreClassPrefix`就可以自动排除前缀，如下所示：
```
<ignoreClassPrefix>App</ignoreClassPrefix>
```
> 注意：由于替换生成后的类名称所以这里要准守驼峰命名规则首字母大写，一次只能配置一个替换前缀。

使用前`AppUserInfoEntity`，使用后`UserInfoEntity`。

#### 模板配置
使用`templates`标签配置自定义的模板列表，一次可以使用单个或者多个模板进行生成，如下配置：
```
<templates>
    <template>
        <name>entity.ftl</name>
        <packageName>entity</packageName>
        <fileSuffix>entity</fileSuffix>
    </template>
    <template>
        <name>service.ftl</name>
        <packageName>service</packageName>
        <fileSuffix>Service</fileSuffix>
    </template>
    <template>
        <name>mapper.ftl</name>
        <packageName>mapper</packageName>
        <fileSuffix>Mapper</fileSuffix>
    </template>
</templates>
```
- `name` ：`freemarker`目录下模板的名称，`必填`
- `packageName`：生成该模板文件后的子包名称，`非必填`
- `fileSuffix`：生成文件的后缀，如：配置后缀为`Entity`，则添加后缀后的文件名为`UserInfoEntity`，后缀首字母会自动根据驼峰转换成大写

### 内置参数
模板驱动数据模型内置了部分参数，`code-builder`准备的每一个参数都是在生成实体类时都可能会用到的。
#### Table参数
- `tableName` `表名`，数据类型：`java.lang.String` 
- `remark`  `表备注信息`，数据类型：`java.lang.String`
- `entityName`  `实体类名称`，如：`user_info`转换为`userInfo`，数据类型：`java.lang.String`
- `columns`  `列列表`，数据类型：`java.util.List<Column>`
- `primaryKeys` `主键列表`，数据类型：`java.util.List<Column>`
- `hasSqlDate` 是否存在`java.sql.Date`类型，`true`：存在，`false`：不存在，数据类型：`java.lang.Boolean`
- `hasTimeStamp` 是否存在`java.sql.TimeStamp`类型，`true`：存在，`false`：不存在，数据类型：`java.lang.Boolean`
- `hasBigDecimal` 是否存在`java.math.BigDecimal`类型，`true`：存在，`false`：不存在，数据类型：`java.lang.Boolean`

##### 使用方式
> `freemarker模板`：`${table.xxx}`，如表名的使用为`${table.tableName}`
	
#### Column参数
- `columnName` `列名`，如：`user_id`，数据类型：`java.lang.String`
- `primaryKey` `是否为主键`，数据类型：`java.lang.Boolean`，`true`：主键，`false`：非主键
- `foreignKey` `是否为外键`，数据类型：`java.lang.Boolean`，`true`：外键，`false`：非外键
- `size` `列长度`，数据类型：`java.lang.Integer`
- `decimalDigits` `小数点精度`，数据类型：`java.lang.Integer`
- `nullable` `列是否为空`，数据类型：`java.lang.Boolean`，`true`：为空，`false`：非空
- `autoincrement` `是否自增`，数据类型：`java.lang.Boolean`，`true`：自增列，`false`：普通列
- `defaultValue` `默认值`，数据类型：`java.lang.String`
- `remark` `列备注`，数据类型：`java.lang.String`
- `jdbcType` `JDBC类型`，对应`java.sql.Types`内类型，数据类型：`java.lang.Integer`
- `jdbcTypeName` `JDBC类型名称`，数据类型：`java.lang.String`
- `javaProperty` `格式化后的属性名称`，如：`userId`，数据类型：`java.lang.String`
- `javaType` `Java数据类型短名`，如：`TimeStamp`，数据类型：`java.lang.String`
- `fullJavaType` `Java数据类型全名`，如：`java.sql.TimeStamp`，数据类型：`java.lang.String`
##### 使用方式
> `freemarker模板`：`${column.xxx}`，如列名的使用为`${column.columnName}`
#### 基础参数
- `className`：Class名称，freemarker指定模板生成文件的类名，模板内配置`${className}`使用
- `packageName`：Package名称，freemarker指定模板生成文件的包名，模板内配置`${packageName}`使用
### 怎么自定义模板？
下面提供一个简单的模板示例，根据上面的`内置参数`可以任意自定义生成文件的内容。
```
<#if (packageName)??>
package ${packageName};
</#if>
import lombok.Data;

<#if (table.hasSqlDate)>
import java.sql.Date;
</#if>
<#if (table.hasTimeStamp)>
import java.sql.Timestamp;
</#if>
<#if (table.hasBigDecimal)>
import java.math.BigDecimal;
</#if>
/**
 * <p>本类代码由code-builder自动生成</p>
 * <p>表名: ${table.tableName} - ${table.remark}</p>
 * ===============================
 * Created with code-builder.
 * User：恒宇少年
 * Date：${.now}
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * ================================
 */
@Data
public class ${className} {
<#list table.primaryKeys as key>
    /**
     * ${key.columnName} - ${key.remark}
     */
    private ${key.javaType} ${key.javaProperty};
</#list>
<#list table.columns as column>
    <#if (!column.primaryKey)>
    /**
     * ${column.columnName} - ${column.remark}
     */
    private ${column.javaType} ${column.javaProperty};
    </#if>
</#list>
}
```
上面是一个数据实体的`freemarker`模板内容，把这个模板存放到`freemarker`目录下，对应在`templates`标签内添加配置就可以完成数据实体的自动创建，创建后的数据实体内容如下所示：
```
package com.code.builder.sample.model;
import lombok.Data;

import java.sql.Timestamp;
/**
 * <p>本类代码由code-builder自动生成</p>
 * <p>表名: app_balance_type - 余额类型信息表</p>
 * ===============================
 * Created with code-builder.
 * User：恒宇少年
 * Date：Jul 17, 2018 9:09:13 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * ================================
 */
@Data
public class BalanceTypeEntity {
    /**
     * BT_ID - 余额类型主键
     */
    private String btId;
    /**
     * BT_NAME - 余额类型名称
     */
    private String btName;
    /**
     * BT_FLAG - 余额类型标识
     */
    private String btFlag;
    /**
     * BT_CREATE_TIME - 添加时间
     */
    private Timestamp btCreateTime;
    /**
     * BT_MARK - 余额类型备注信息
     */
    private String btMark;
}
```
### 创建的实体类去了哪里？
创建的实体类会在`target/generated-sources/java`目录下，如果你配置`packagePrefix`参数，会自动在生成目录下创建`packagePrefix`配置值的子目录。
如：
```
<packagePrefix>com.code.builder.sample</packagePrefix>
```
则最终创建的生成根目录为：`target/generated-sources/java/com/code/builder/sample`
### 怎么使用？
#### SpringBoot 方式使用
1. `运行项目`就可以根据配置生成对应的文件
#### Maven Plugin 方式使用
1. 执行`mvn clean`命令用于清空`target`目录下的内容
2. 执行`mvn compile`命令编译项目并且生成`实体类`
 
### 为什么SpringBoot方式不用配置数据库信息？
`Maven Plugin`方式是通过配置的`数据库连接信息`以及数据库连接驱动获取数据库连接对象`Connection`后来操作`JDBC元数据`。

而`SpringBoot`方式则是直接使用项目中配置的`DataSource`对象实例来进行获取的`Connection`数据库连接对象后来操作`JDBC元数据`。
> 注意：如果你是多数据源项目，默认会使用`primary`数据源实例。
