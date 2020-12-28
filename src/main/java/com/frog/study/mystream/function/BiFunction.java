package com.frog.study.mystream.function;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
@FunctionalInterface
public interface BiFunction<R, T, U> {

    /**
     * 函数式接口
     * 类似于 z = F(x,y)
     * */
    R apply(T t, U u);
}
