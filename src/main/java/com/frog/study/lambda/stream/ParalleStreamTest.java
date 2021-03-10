package com.frog.study.lambda.stream;

import org.junit.rules.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 并发流和CompletableFuture的配合使用
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/28 8:59 下午
 */
public class ParalleStreamTest {
    //并发流和CompletableFuture的配合使用
    /*public static void main(String[] args)  throws Exception{
        List<Integer> demo = Stream.iterate(0, item -> item + 1)
                .limit(5)
                .collect(Collectors.toList());
        //示例1
        Stopwatch stopwatch = Stopwatch.createStarted(Ticker.systemTicker());
        demo.stream().forEach(item -> {
            try {
                Thread.sleep(500);
                System.out.println("示例1-"+Thread.currentThread().getName());
            } catch (Exception e) { }
        });
        System.out.println("示例1-"+stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));

        //示例2, 注意需要ForkJoinPool，parallelStream才会使用executor指定的线程，否则还是用默认的 ForkJoinPool.commonPool()
        ExecutorService executor = new ForkJoinPool(10);
        stopwatch.reset(); stopwatch.start();
        CompletableFuture.runAsync(() -> demo.parallelStream().forEach(item -> {
            try {
                Thread.sleep(1000);
                System.out.println("示例2-" + Thread.currentThread().getName());
            } catch (Exception e) { }
        }), executor).join();
        System.out.println("示例2-"+stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        //示例３
        stopwatch.reset(); stopwatch.start();
        demo.parallelStream().forEach(item -> {
            try {
                Thread.sleep(1000);
                System.out.println("示例3-"+Thread.currentThread().getName());
            } catch (Exception e) { }
        });
        System.out.println("示例3-"+stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        executor.shutdown();
    }*/
}
