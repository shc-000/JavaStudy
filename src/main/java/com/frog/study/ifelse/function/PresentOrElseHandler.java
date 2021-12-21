package com.frog.study.ifelse.function;

import java.util.function.Consumer;

/**
 * 【空值与非空值分支处理】
 * 如果存在,执行消费操作，否则执行基于空的操作
 * 创建一个名为PresentOrElseHandler的函数式接口，
 * 接口的参数一个为Consumer接口,一个为Runnable,分别代表值不为空时执行消费操作和值为空时执行的其他操作
 *
 * @author shaohaichao
 * @since 2021/12/6
 */
@FunctionalInterface
public interface PresentOrElseHandler<T extends Object> {
    /**
     * 值不为空时执行消费操作
     * 值为空时执行其他的操作
     *
     * @param action      值不为空时，执行的消费操作
     * @param emptyAction 值为空时，执行的操作
     **/
    void presentOrElseHandle(Consumer<? super T> action, Runnable emptyAction);
}
