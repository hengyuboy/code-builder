package com.gitee.hengboy.builder.core.configuration;

import lombok.Data;

/**
 * 模板配置基本信息
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/12
 * Time：4:45 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
public class TemplateConfiguration {
    /**
     * 模板名称
     * 如：entity.ftl
     */
    private String name;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 生成文件的后缀名
     */
    private String fileSuffix;
}
