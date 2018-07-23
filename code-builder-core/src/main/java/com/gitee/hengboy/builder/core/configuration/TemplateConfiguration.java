package com.gitee.hengboy.builder.core.configuration;
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
     * 如：model.ftl
     */
    private String name;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 生成文件的前缀名
     * 如：DXxx，D则是前缀
     */
    private String filePrefix;
    /**
     * 生成文件的后缀名
     */
    private String fileSuffix;
}
