package com.frog.study.functionInterface;

/**
 * 函数式接口2
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/21
 */
@FunctionalInterface
public interface FunctionInterfaceTest2 {
    String getInfo(String input);

    default void print() {
        System.out.println("test");
    }
    static void identity() {
        System.out.println("identity");
    }
}
