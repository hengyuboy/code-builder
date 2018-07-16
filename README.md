## 欢迎使用代码生成器code-builder

[toc]

### 加入群聊
SpringBoot 核心技术①：373229384 （满）
SpringBoot 核心技术②：588351309
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
由于`code-builder`是`Maven mojo`插件的形式创建的，所以我们只需要在项目的`pom.xml`文件内添加`plugin`插件配置，如下所示：
```
<plugin>
    <groupId>com.gitee.hengboy</groupId>
    <artifactId>code-builder</artifactId>
    <version>1.0.0.RELEASE</version>
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
- `dbDriverClassName`：数据库驱动类名，根据不用的数据库类型配置不同的驱动类名，默认根据`dbType`使用内部定义的类名，如需自定义可以设置。

#### 数据库基本信息配置
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

### 内置参数

### 怎么使用？

### 下一版本更新内容