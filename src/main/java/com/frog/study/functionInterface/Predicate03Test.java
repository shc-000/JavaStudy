package com.frog.study.functionInterface;

import java.util.function.Predicate;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:24 下午
 */
//默认方法: or
//一个条件满足
    /*
        定义一个方法,方法的参数,传递一个字符串
        传递两个Predicate接口
            一个用于判断字符串的长度是否大于5
            一个用于判断字符串中是否包含a
            一个条件必须满足
     */
public class Predicate03Test {
    public static boolean checkString(String s, Predicate<String> pred1, Predicate<String> pred2){
        return pred1.or(pred2).test(s);
        // 等价于return pre1.test(s) || pre2.test(s);
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
