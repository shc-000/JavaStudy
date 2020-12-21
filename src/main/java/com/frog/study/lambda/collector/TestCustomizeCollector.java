package com.frog.study.lambda.collector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/20 10:12 下午
 */
public class TestCustomizeCollector {
    public static void main(String[] args) {
        Collector<String, List<String>, List<String>> collector = new CustomizeCollector<>();
        String[] arrs = new String[]{"Alex", "Wang", "Hello", "Collection", "java 8", "Stream"};
        //使用自定义的收集器
        List<String> result = Arrays.stream(arrs).filter(s -> s.length() >= 5)
                .collect(collector);

        System.out.println(result);

        //每次累加1，累加10次，求和
        long  sum = Stream.iterate(1L,i->i+1).limit(10).reduce(0L,Long::sum);
        System.out.println(sum);
    }
}
