package com.frog.study.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/17 9:58 下午
 */
public class NumericStreamTest {
    //IntStream(相当于使用拆箱后的结果，节约内存占用量),LongStream,DoubleStream...

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
