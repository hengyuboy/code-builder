package com.gitee.hengboy.builder.common;

import com.gitee.hengboy.builder.core.engine.EngineTemplate;
import com.gitee.hengboy.builder.core.engine.impl.FreemarkerEngineImpl;
import lombok.Getter;

/**
 * 驱动类型枚举
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/11
 * Time：5:54 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Getter
public enum EngineTypeEnum {
    /**
     * freeMarker模板类型
     */
    FREEMARKER("freemarker", FreemarkerEngineImpl.class);
    /**
     * 存放模板文件夹名称
     */
    private String templateDirName;
    /**
     * 模板驱动实现类类型
     */
    private Class<? extends EngineTemplate> implClass;

    EngineTypeEnum(String templateDirName, Class<? extends EngineTemplate> implClass) {
        this.templateDirName = templateDirName;
        this.implClass = implClass;
    }
}
