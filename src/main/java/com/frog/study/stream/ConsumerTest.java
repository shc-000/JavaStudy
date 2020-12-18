package com.frog.study.stream;

import java.util.function.Consumer;

/*
 * Java8 内置的四大核心函数式接口
 *
 * Consumer<T> : 消费型接口
 *   void accept(T t);
 *
 * Supplier<T> : 供给型接口
 *   T get();
 *
 * Function<T, R> : 函数型接口
 *   R apply(T t);
 *
 * Predicate<T> : 断言型接口
 *   boolean test(T t);
 *
 */
public class ConsumerTest {
    public static void main(String[] args) {
        happy(10000, (m) -> System.out.println("每次消费：" + m + "元"));
    }
    public static void happy(double money, Consumer<Double> con){
        con.accept(money);
    }
}
