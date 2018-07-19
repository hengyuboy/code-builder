package com.gitee.hengboy.builder.common.enums;
/**
 *  Copyright 2018 恒宇少年
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
