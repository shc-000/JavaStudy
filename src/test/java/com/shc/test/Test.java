package com.shc.test;

import java.util.function.BinaryOperator;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/1/4
 */
public class Test {

    @org.junit.Test
    public void test(){
        BinaryOperator<Long> binaryOperator = (x, y) -> x + y;

        System.out.println(binaryOperator.apply(1L,2L).toString());
    }
}
