package com.frog.study.lambda.function;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/18
 */
public class FunctionTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3", "11", "22", "33");
        getLength(list, String::length);

        String subStr = strHandler("大威武", (str) -> str.substring(2, 5));
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
}
