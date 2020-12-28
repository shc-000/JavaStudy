package com.frog.study.mystream.function;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
@FunctionalInterface
public interface Function<R,T> {

    /**
     * 函数式接口
     * 类似于 y = F(x)
     * */
    R apply(T t);
}
