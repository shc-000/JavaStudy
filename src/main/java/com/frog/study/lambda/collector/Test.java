package com.frog.study.lambda.collector;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/7/15 9:07 下午
 */
public class Test {
    public static void main(String[] args) {
//        List<Person> data = getPersonList();
//        Map<Integer, List<String>> collect = data.stream().collect(new NameCollector());
//        collect.entrySet().forEach(a -> System.out.println("key:" + a.getKey() + " value:" + a.getValue()));
        List<OrderDto> data = getOrderDtoList();
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "3");

        Map<String,MutiCalcModel> map = data.parallelStream().collect(new MutiCalcCollector());
        map.entrySet().forEach(a -> System.out.println("key:" + a.getKey() + " value:" + a.getValue().toString()));
    }

    private static List<OrderDto> getOrderDtoList() {
        return Arrays.asList(
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3),
                new OrderDto(3, "error", 4),
                new OrderDto(4, "info", 5),
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3),
                new OrderDto(3, "error", 4),
                new OrderDto(4, "info", 5),
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3),
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3),
                new OrderDto(3, "error", 4),
                new OrderDto(4, "info", 5),
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3),
                new OrderDto(3, "error", 4),
                new OrderDto(4, "info", 5),
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3),
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3),
                new OrderDto(3, "error", 4),
                new OrderDto(4, "info", 5),
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3),
                new OrderDto(3, "error", 4),
                new OrderDto(4, "info", 5),
                new OrderDto(1, "succ", 2),
                new OrderDto(2, "warn", 3)
        );
    }

    private static List<Person> getPersonList() {
        return Arrays.asList(
                new Person("小花", 12),
                new Person("小明", 14),
                new Person("小马", 14),
                new Person("小坏", 13),
                new Person("小红", 13),
                new Person("小赵", 14)
        );
    }
}
