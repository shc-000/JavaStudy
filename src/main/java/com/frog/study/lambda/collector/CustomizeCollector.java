package com.frog.study.lambda.collector;

import org.omg.CORBA.portable.IDLEntity;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * 自定义collector
 * Collector<T,A,R>:T代表stream元素的类型，A代表要创建成的Collector的类型，R代表最终要返回的
 *
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/20 9:59 下午
 */
public class CustomizeCollector<T> implements Collector<T, List<T>, List<T>> {

    private void log(final String log){
        System.out.println(Thread.currentThread().getName()+"-"+log);
    }

    @Override
    public Supplier<List<T>> supplier() {
        log("supplier");
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        log("accumulator");
        //accumulator：累加器
        return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        log("combiner");
        //始终把中间结果放在第一个list中
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        log("finisher");
        //相当于：t->t,是谁就返回谁
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        log("characteristics");
        //支持并行
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }
}
