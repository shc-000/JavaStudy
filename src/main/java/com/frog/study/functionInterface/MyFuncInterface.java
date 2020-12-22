package com.frog.study.functionInterface;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
@FunctionalInterface
public interface MyFuncInterface<T> {
    T  getValue(T t);
}

