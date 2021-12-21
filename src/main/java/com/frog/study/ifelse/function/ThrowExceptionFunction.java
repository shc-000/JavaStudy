package com.frog.study.ifelse.function;

/**
 * 小技巧【处理抛出异常的if】
 * 抛异常接口(定义一个抛出异常的形式的函数式接口, 这个接口只有参数没有返回值是个消费型接口)
 *
 * @author shaohaichao
 * @since 2021/12/6
 */
@FunctionalInterface
public interface ThrowExceptionFunction {
    /**
     * 抛出异常信息
     *
     * @param message 异常信息
     **/
    void throwMessage(String message);
}
