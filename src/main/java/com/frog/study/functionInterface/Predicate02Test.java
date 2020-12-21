package com.frog.study.functionInterface;

import java.util.function.Predicate;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:23 下午
 */
/*
        定义一个方法,方法的参数,传递一个字符串
        传递两个Predicate接口
            一个用于判断字符串的长度是否大于5
            一个用于判断字符串中是否包含a
            两个条件必须同时满足
     */
public class Predicate02Test {
    public static boolean checkString(String s, Predicate<String> pred1, Predicate<String> pred2){
        return pred1.and(pred2).test(s);
        // 等价于return pre1.test(s) && pre2.test(s);
    }
    public static void main(String[] args){
        //定义一个字符串
        String s = "abcdef";
        // 调用方法,进行判断
        boolean b = checkString(s,(String str) ->{
            return str.length()>5;
        }, (String str) ->{
            return str.contains("a");
        });
        System.out.println(b);
    }
}
