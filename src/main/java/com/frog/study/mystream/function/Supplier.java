package com.frog.study.mystream.function;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
@FunctionalInterface
public interface Supplier<T> {

    /**
     * 提供初始值
     * @return 初始化的值
     * */
    T get();
}

