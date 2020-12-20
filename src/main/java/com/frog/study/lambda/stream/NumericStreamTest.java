package com.frog.study.lambda.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/17 9:58 下午
 */
public class NumericStreamTest {
    /*在java.util.function包中，我们可以看到IntPredicate、IntFunction、IntSupplier、IntConsumer、LongConsumer、LongFunction、LongPredicate、LongSupplier等，
    是为了在输入和输出都是原始类型时，避免自动装箱的操作。
    java的自动装箱机制虽然可以让我们在原始类型和引用类型之间的装箱和拆箱操作是自动完成的，
    但这在性能方面是要付出代价的。装箱后的值本质上就是把原始类型包裹起来，并保存在堆里。
    因此，装箱后的值需要更多的内存，并需要额外的内存搜索来获取被包裹的原始值。
    比如下面这个，是没有装箱的：
    IntPredicate evenNumbers = (int i) -> i % 2 == 0;
    evenNumbers.test(1000);
    下面这个，是有装箱的：
    Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;
    oddNumbers.test(1000);
    */

    public static void main(String[] args) {

    }

    private void intStream() {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
        //转化为intStream(拆箱操作)，减少内存占用
        IntStream intStream = stream.mapToInt(i -> i.intValue());

        //有时候操作的别的函数时，基本类型不支持，需要做装箱操作
        //转化为Integer,装箱操作（boxed()）
        int a = 9;
        //勾股定理3，4，5
        IntStream.rangeClosed(1, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .boxed()//或者去掉这里，下面改成mapToObj也可以
                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                .forEach(r -> System.out.println("a=" + r[0] + ",b=" + r[1] + ",c=" + r[2]));

    }

}
