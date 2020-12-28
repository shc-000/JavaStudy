package com.frog.study.functionInterface;

/**
 * 函数式接口1
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/21
 */
@FunctionalInterface
public interface FunctionInterfaceTest {
    String getInfo(String input);

    @Override
    String toString();  //Object中的方法

    @Override
    boolean equals(Object obj); //Object中的方法

    default void print() {
        System.out.println("test");
    }

    static void identity() {
        System.out.println("identity");
    }
}
