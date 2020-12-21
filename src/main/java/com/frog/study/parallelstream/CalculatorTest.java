package com.frog.study.parallelstream;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.LongStream;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/21
 */
//综合测试
public class CalculatorTest {
    public long[] arr;
    @Before
    public void resource(){
        arr = LongStream.rangeClosed(0, 10000000).toArray();
    }

    //1. 循环测试
    @Test
    public void test1(){
        Instant start = Instant.now();

        long sum = 0L;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        Instant end = Instant.now();
        System.out.println("耗时: "+ Duration.between(start,end).toMillis()+"ms");
        System.out.println(sum);
    }

    //2. ExecutorService线程池测试
    @Test
    public void test2(){
        Instant start = Instant.now();

        long sum = new ExecutorServiceCalculator().sum(arr);

        Instant end = Instant.now();
        System.out.println("耗时: "+ Duration.between(start,end).toMillis()+"ms");
        System.out.println(sum);
    }

    //3. forkJoin线程池测试
    @Test
    public void test3(){
        Instant start = Instant.now();

        long sum = new ForkJoinCalculator().sum(arr);

        Instant end = Instant.now();
        System.out.println("耗时: "+ Duration.between(start,end).toMillis()+"ms");
        System.out.println(sum);
    }

    //4. parallelStream测试
    @Test
    public void test4(){
        Instant start = Instant.now();

        long sum = Arrays.stream(arr).parallel().sum();

        Instant end = Instant.now();
        System.out.println("耗时: "+ Duration.between(start,end).toMillis()+"ms");
        System.out.println(sum);
    }
}
