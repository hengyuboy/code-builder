package com.gitee.hengboy.builder.core.engine;
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
