package com.frog.study.lambda.collector;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/7/15 9:06 下午
 */
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@Slf4j
public class NameCollector implements Collector<Person, Map<Integer, List<String>>, Map<Integer, List<String>>> {

    /**
     * 容器的提供者
     *
     * @return
     */
    @Override
    public Supplier<Map<Integer, List<String>>> supplier() {
        log.info("supplier invoke ....");
        return HashMap::new;
    }

    /**
     * 累加操作
     *
     * @return
     */
    @Override
    public BiConsumer<Map<Integer, List<String>>, Person> accumulator() {
        log.info("accumulator invoke ....");
        return (map, ele) -> {
            Integer age = ele.getAge();
            String name = ele.getName();
            if (map.containsKey(age)) {
                List<String> list = map.get(age);
                list.add(name);
                map.put(age, list);
            } else {
                List<String> list = new ArrayList<>();
                list.add(name);
                map.put(age, list);
            }
        };
    }

    /**
     * 并发的情况将每个线程的中间容器A合并
     *
     * @return
     */
    @Override
    public BinaryOperator<Map<Integer, List<String>>> combiner() {
        log.info("combiner invoke ....");
        return (left, right) -> {
            right.entrySet().forEach(a -> {
                Integer key = a.getKey();
                List<String> value = a.getValue();
                if (left.containsKey(key)) {
                    List<String> list = left.get(key);
                    list.addAll(value);
                    left.put(key, list);
                } else {
                    left.put(key, value);
                }
            });
            return left;
        };
    }

    /**
     * 终止操作
     * 这里可以不用终止操作 直接返回中间结果
     * characteristics 标记 IDENTITY_FINISH
     *
     * @return
     */
    @Override
    public Function<Map<Integer, List<String>>, Map<Integer, List<String>>> finisher() {
        log.info("finisher invoke ....");
        return (a) -> a;
    }

    @Override
    public Set<Characteristics> characteristics() {
        log.info("characteristics invoke ....");
        return Collections.unmodifiableSet(
                /*
                // finisher 不会执行
                EnumSet.of(
                        Characteristics.UNORDERED,
                        Characteristics.CONCURRENT,
                        Characteristics.IDENTITY_FINISH
                )
                */
                // finisher 会执行
                EnumSet.of(
                        Characteristics.UNORDERED,
                        Characteristics.CONCURRENT
                )
        );
    }
}