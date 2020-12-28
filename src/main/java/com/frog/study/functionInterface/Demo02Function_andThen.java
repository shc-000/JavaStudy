package com.frog.study.functionInterface;

import java.util.function.Function;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:27 下午
 */
//默认方法: andThen
//
//和Consumer中的andThen一样
public class Demo02Function_andThen {
    /*
        定义一个方法
        参数串一个字符串类型的整数
        参数再传递两个Function接口
            一个泛型使用Function<String,Integer>
            一个泛型使用Function<Integer,String>
     */
    public static void change(String s, Function<String, Integer> fun1, Function<Integer, String> fun2) {
        String ss = fun1.andThen(fun2).apply(s);
        System.out.println(ss);
    }

    public static void main(String[] args) {
        //定义一个字符串类型的整数
        String s = "123";
        //调用change方法,传递字符串和两个Lambda表达式
        change(s, (String str) -> {
            //把字符串转换为整数+10
            return Integer.parseInt(str) + 10;
        }, (Integer i) -> {
            //把整数转换为字符串
            return i + "";
        });

        //优化Lambda表达式
        change(s, str -> Integer.parseInt(str) + 10, i -> i + "");
    }
}
