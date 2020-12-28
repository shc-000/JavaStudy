package com.frog.study.mystream.function;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
@FunctionalInterface
public interface Predicate <T>{

    /**
     * 函数式接口
     * @param item 迭代的每一项
     * @return true 满足条件
     *          false 不满足条件
     * */
    boolean satisfy(T item);
}
