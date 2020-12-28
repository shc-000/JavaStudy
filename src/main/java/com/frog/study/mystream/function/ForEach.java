package com.frog.study.mystream.function;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
@FunctionalInterface
public interface ForEach <T>{

    /**
     * 迭代器
     * @param item 被迭代的每一项
     * */
    void apply(T item);
}
