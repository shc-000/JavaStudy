package com.frog.study.lambda2.functionInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/1/24
 */
public class FunctionalInterfaceTest {
    //1. 断⾔言型接⼝口:Predicate<T> 将满⾜足条件的字符串串放⼊入集合
    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> newList = new ArrayList<>();
        for (String s : list) {
            if (predicate.test(s)) {
                newList.add(s);
            }
        }
        return newList;
    }

    //2.函数型接⼝口:Function<T, R> 处理理字符串串
    public String strHandler(String str, Function<String, String> function) {
        return function.apply(str);
    }

    //3.供给型接⼝口 :Supplier<T> 产⽣生指定个数的整数，并放⼊入集合
    public List<Integer> getNumList(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = supplier.get();
            list.add(n);
        }
        return list;
    }

    //4.消费型接⼝口 :Consumer<T> 修改参数
    public void modifyValue(Integer value, Consumer<Integer> consumer) {
        consumer.accept(value);
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "java8", "function", "predicate");
        FunctionalInterfaceTest test = new FunctionalInterfaceTest();
        //1.测试断言性接口
        List<String> newList = test.filterStr(list, s -> s.length() > 5);
        for (String s : newList) {
            System.out.println(s);
        }

        //2.测试函数性接口
        String str1 = test.strHandler("测试内置函数式接⼝口", s -> s.substring(2));
        System.out.println(str1);
        String str2 = test.strHandler("abcdefg", s -> s.toUpperCase());
        System.out.println(str2);

        //3.测试供给型接口
        List<Integer> numList = test.getNumList(10, () -> (int) (Math.random() * 100));
        numList.forEach(System.out::println);

        //4.测试消费型接口
        test.modifyValue(3, s -> System.out.println(s * 3));
    }

}
