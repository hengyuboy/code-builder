package com.gitee.hengboy.builder.core.database.model;
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
import lombok.Builder;
import lombok.Data;

/**
 * 数据表内的主键、外键对象
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/8
 * Time：5:32 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
@Builder
public class Key {
    /**
     * 主键表名
     */
    private String pkTableName;
    /**
     * 主键列名
     */
    private String pkColumnName;
    /**
     * 外键表名
     */
    private String fkTableName;
    /**
     * 外键列名
     */
    private String fkColumnName;
    /**
     * 外键中的序列号（值1表示外键的第一列，值2表示外键中的第二列）。
     */
    private Integer seq;

}
