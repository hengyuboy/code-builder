package com.gitee.hengboy.builder.core.invoke;

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.enums.ErrorEnum;
import com.gitee.hengboy.builder.common.exception.CodeBuilderException;
import com.gitee.hengboy.builder.common.util.StringUtil;
import com.gitee.hengboy.builder.core.database.DataBase;
import com.gitee.hengboy.builder.core.database.DataBaseFactory;
import com.gitee.hengboy.builder.core.engine.EngineTemplate;
import com.gitee.hengboy.builder.core.engine.EngineTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 自动生成执行类型
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/19
 * Time：1:59 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class CodeBuilderInvoke {

    private CodeBuilderInvoke() { }

    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(CodeBuilderInvoke.class);

    /**
     * 执行构造入口方法
     * 该方法用于maven-plugin、starter两个地方
     *
     * @param codeBuilderProperties 自动生成参数实体
     */
    public static void invoke(CodeBuilderProperties codeBuilderProperties) {
        if (!codeBuilderProperties.isExecute()) {
            logger.info("未开启自动代码生成，如需生成实体类请配置【execute=true】");
            return;
        }

        // 获取数据库对象
        DataBase dataBase = DataBaseFactory.newInstance(codeBuilderProperties);
        // 获取驱动模板
        EngineTemplate engineTemplate = EngineTemplateFactory.newInstance(codeBuilderProperties.getEngineTypeEnum(), dataBase, codeBuilderProperties);
        // 获取表名列表
        List<String> tableNames = getTables(dataBase, codeBuilderProperties);
        // 执行循环自动生成文件
        engineTemplate.loopProcess(tableNames);
        // 关闭数据库连接
        dataBase.closeConnection();
    }

    /**
     * 获取需要自动生成的表列表
     *
     * @param dataBase 数据库对象实例
     * @return
     */
    private static List<String> getTables(DataBase dataBase, CodeBuilderProperties codeBuilderProperties) {
        List<String> tables = codeBuilderProperties.getTables();
        String generatorByPattern = codeBuilderProperties.getGeneratorByPattern();
        /*
         * 根据配置tables参数表名进行构建生成
         * 优先级高于generatorByPattern
         */
        if (null != tables && tables.size() > 0) {
            logger.info("Using table name to generate code automatically, please wait...");
            return tables;
        }
        /*
         * 如果配置generatorByPattern参数，优先级高于tables
         */
        else if (StringUtil.isNotEmpty(generatorByPattern)) {
            logger.info("Using expression method to generate code automatically, please wait...");
            return dataBase.getTableNames(generatorByPattern);
        }
        throw new CodeBuilderException(ErrorEnum.NO_BUILDER_TABLE);
    }
}
