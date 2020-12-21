package com.frog.study.functionInterface;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:22 下午
 */
//java.util.function.Predicate<T>对某一种数据类型进行怕吗吨, 结果返回一个boolean值
//
//抽象方法: test
//Predicate忌口中抽象方法:boolean test(T t)用来对指定数据类型数据进行判断的方法
public class Predicate01Test {
    /* 定义一个方法
    参数传递一个String类型的字符串
    传递一个Predicate接口, 泛型使用String
    使用predicate中的方法test对字符串进行判断, 将判断的结果返回
    */
    public static boolean checkString(String s, Predicate<String> pre){
        return pre.test(s);
    }
    public static void main(String[] args){
        //定义一个字符串
        String s = "abcdef";
        //调用checkString方法对字符串进行校验,参数传递字符串和Lambda表达式
        /*boolean b = checkString(s,(String str)->{
            //对参数传递的字符串进行判断,判断字符串的长度是否大于5,并把判断的结果返回
            return str.length()>5;
        });*/

        //优化Lambda表达式
        boolean b = checkString(s,str->str.length()>5);
        System.out.println(b);
    }

//    默认方法:and
//            两个条件必选同时满足

//    default Predicate<T> and(Predicate<? super T> other) {
//        Objects.requireNonNull(other);
//        return (t) ‐> test(t) && other.test(t);
//    }
/*
        定义一个方法,方法的参数,传递一个字符串
        传递两个Predicate接口
            一个用于判断字符串的长度是否大于5
            一个用于判断字符串中是否包含a
            两个条件必须同时满足
     */
}
