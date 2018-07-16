package com.gitee.hengboy.builder.core.engine;

import com.gitee.hengboy.builder.core.database.model.Table;

import java.util.List;

/**
 * 驱动模板接口
 * 目前仅实现freemarker模板
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:15 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public interface EngineTemplate {
    /**
     * 单个数据表生成文件
     *
     * @param table 数据表对象
     */
    void process(Table table);

    /**
     * 循环执行生成文件
     *
     * @param tableNames 表名列表
     */
    void loopProcess(List<String> tableNames);
}
