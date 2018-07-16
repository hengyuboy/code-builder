package com.gitee.hengboy.builder.core.configuration;

import com.gitee.hengboy.builder.common.StringUtil;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * 代码生成配置
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/12
 * Time：4:44 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
public class BuilderConfiguration {
    /**
     * 模板配置列表
     */
    private List<TemplateConfiguration> templates;
    /**
     * package包名前缀
     */
    private String packagePrefix;

    /**
     * 获取不同系统的package转换目录分割后的路径
     *
     * @return 获取不同系统格式化后的包名路径
     */
    public String getDiffSysPackagePrefix() {
        if (!StringUtil.isNotEmpty(packagePrefix)) {
            return packagePrefix;
        }
        return packagePrefix.replace(".", File.separator);
    }
}
