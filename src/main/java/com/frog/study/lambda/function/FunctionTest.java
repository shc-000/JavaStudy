package com.frog.study.lambda.function;

import java.util.*;
import java.util.function.Function;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/18
 */
public class FunctionTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("abcdef", "2bcdef", "3bcdefa", "11bcdefqq", "22bcdefa", "33bcdefg");
        getLength(list, String::length);

        strHandler(list, (str)-> str.substring(2, 5));

        String subStr = strHandler("大威武123asd", (str) -> str.substring(2, 5));
        System.out.println(subStr);
    }
    public static <T, R> void getLength(List<T> list, Function<T, R> function) {
        Map<T, R> map = new HashMap<>();
        for (T t : list) {
            map.put(t, function.apply(t));
        }
        System.out.println(map);
    }

    //需求：用于处理字符串
    public static String strHandler(String str, Function<String, String> fun){
        return fun.apply(str);
    }

    public static List<String> strHandler(List<String> list, Function<String,String> fun){
        List<String> list1 = new ArrayList<>();
        for(String str : list){
            list1.add(fun.apply(str));
        }
        System.out.println(list1);
        return list1;
    }
}
