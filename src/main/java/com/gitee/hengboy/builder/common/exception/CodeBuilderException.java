package com.gitee.hengboy.builder.common.exception;

import com.gitee.hengboy.builder.common.enums.ErrorEnum;

/**
 * 代码生成异常定义
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/17
 * Time：11:33 AM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class CodeBuilderException extends RuntimeException {
    /**
     * 初始化代码异常
     *
     * @param errorEnum 错误枚举
     * @param params    枚举内字符串参数占位数组
     */
    public CodeBuilderException(ErrorEnum errorEnum, String... params) {
        super(String.format(errorEnum.getMessage(), params));
    }
}
