package com.frog.study.lambda.function;

import java.util.function.Predicate;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/17 8:54 下午
 */
public class PredicateTest {
    public static void main(String[] args) {
        //函数作为参数传递
        int result = find(new Integer[]{1, 2, 3, 4, 5, 6}, -1, i -> i < 10);
        System.out.println(result);
    }

    private static int find(Integer[] values, int defaultValue, Predicate<Integer> predicate) {
        for (int i : values) {
            if (predicate.test(i)) {
                return i;
            }
        }
        return defaultValue;
    }
}
