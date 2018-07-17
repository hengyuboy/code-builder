package com.gitee.hengboy.builder.core.engine.model;

import com.gitee.hengboy.builder.core.database.model.Table;
import lombok.Builder;
import lombok.Data;

/**
 * 模板结果实体
 * 没一个模板都会有该实体的对象实例传递
 * 比如：freemarker在process时传递该实体的实例到freemarker模板内
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/17
 * Time：10:29 AM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
@Builder
public class DataModelEntity {
    /**
     * 表格实例
     */
    private Table table;
    /**
     * 类名
     * 如：UserInfoEntity
     */
    private String className;
    /**
     * 包名
     * 如：com.xxx.xxx.user
     */
    private String packageName;
}
