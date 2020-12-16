package com.frog.study;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/16 10:53 下午
 */
public class StreamTest {
    public static void main(String[] args) {
        skipVSLimit();
        flatMap();
    }

    private static void skipVSLimit() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        list.stream().skip(5).forEach(System.out::println);
        list.stream().limit(5).forEach(System.out::println);
    }

    private static void flatMap() {
        //扁平化
        String[] words = {"hello", "world"};
        //过滤掉重复的字母
        Stream<String[]> stream = Arrays.stream(words).map(w -> w.split(""));
        stream.flatMap(Arrays::stream).distinct().forEach(System.out::println);
    }
}
