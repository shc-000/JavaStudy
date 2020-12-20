package com.frog.study.lambda.function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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
public class SupplierTest {

    public static void main(String[] args) {
        List<Integer> numList = getNumList(10, () -> (int)(Math.random() * 100));

        for (Integer num : numList) {
            System.out.println(num);
        }
    }

    //需求：产生指定个数的整数，并放入集合中
    public static List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }

        return list;
    }
}
