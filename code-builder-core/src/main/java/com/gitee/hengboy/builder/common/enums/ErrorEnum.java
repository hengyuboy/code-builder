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
import lombok.Getter;

/**
 * 错误信息枚举
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/9
 * Time：2:35 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Getter
public enum ErrorEnum {
    NOT_GET_TABLE("未获取到任何表."),
    NOT_GET_CONNECTION("无法获取数据库连接."),
    NOT_GET_COMMENT("无法获取表%s的备注信息."),
    NOT_GET_PRIMARY_KEYS("无法获取表%s内主键列表."),
    NOT_GET_COLUMN("无法获取表%s内的数据列列表"),
    NOT_ALLOW_DB_TYPE("不支持的数据库类型."),
    NOT_ALLOW_ENGINE("不支持的驱动模板类型."),
    NO_BUILDER_TABLE("请配置tables或者generatorByPattern参数后使用自动生成.");

    ErrorEnum(String message) {
        this.message = message;
    }

    private String message;
}
