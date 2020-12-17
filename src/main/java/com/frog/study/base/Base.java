package com.frog.study.base;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/17
 */
public class Base {
    public static void main(String[] args) {
        dataType();
    }

    private static void dataType() {
        double i = 0.05;
        double j = 0.01;

        double d1 = i - j;
        double d2 = i + j;
        System.out.println(d1);
        System.out.println(d2);

        float a = 2.1f;
        float b = 0.1f;

        float f1 = a - b;
        float f2 = a + b;

        System.out.println(f1);
        System.out.println(f2);
    }
}
