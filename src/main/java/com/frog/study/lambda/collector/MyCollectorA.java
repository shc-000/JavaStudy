package com.frog.study.lambda.collector;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author shaohaichao
 * @since 2021/12/20
 */
public class MyCollectorA<T> implements Collector<T, Set<T>, Map<T, T>> {
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked...");
        return HashSet::new;//实例化一个存放中间结果的集合Set
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked...");
        return (item1, item2) -> {

            /**
             *  *  A a1 = supplier.get();
             *     accumulator.accept(a1, t1);
             *     accumulator.accept(a1, t2);
             *     R r1 = finisher.apply(a1);  // result without splitting
             *
             *     A a2 = supplier.get();
             *     accumulator.accept(a2, t1);
             *     A a3 = supplier.get();
             *     accumulator.accept(a3, t2);
             *     R r2 = finisher.apply(combiner.apply(a2, a3));  // result with splitting
             */

            // System.out.println("current thread : "+item1+" , "+Thread.currentThread().getName());
            item1.add(item2);//将遍历的每个元素加入到Set当中
        };

    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked...");
        return (item1, item2) -> {
            item1.addAll(item2);//多线程下，集合Set的合并操作
            System.out.println("--------");
            return item1;
        };
    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher() {
        System.out.println("finisher invoked...");
        return (item1) -> {
            Map<T, T> rm = new HashMap<T, T>();
            item1.stream().forEach((bean) -> rm.put(bean, bean));//将Set集合的每个元素加入到新的map之中
            return rm;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked...");
        return Collections.unmodifiableSet(
                EnumSet.of(Characteristics.CONCURRENT, Characteristics.CONCURRENT));//支持并发操作，并且是不能保证原始集合的顺序。
    }

    // TODO-SHC: 2021/12/20 可以看到收集器的特性被设置成CONCURRENT，并且是parallelStream，执行过程中没有调用combiner()方法。
    //  因为只有一个公用的容器没必要再去掉combiner()合并中间结果。PS：在单线程模式下，并且特性设置成CONCURRENT，combiner()会被调用。
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "welcome", "helloworld", "helloworldA");
        Set<String> set = new HashSet<>();
        set.addAll(list);
        Map<String, String> maped = set.parallelStream().collect(new MyCollectorA<>());
    }

   // 当收集器的特性包含IDENTITY_FINISH特性时，会把收集器内部的中间集合A强制转换为R（当中间容器类型A和结果类型R不同时，但是又设置了IDENTITY_FINISH特性，那么会抛出java.lang.ClassCastException），否则才会调用收集器的finisher()方法。
}
