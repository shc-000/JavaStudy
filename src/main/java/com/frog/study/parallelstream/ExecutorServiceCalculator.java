package com.frog.study.parallelstream;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/21
 */
//ExecutorService类(用于测试executorService线程池)
public class ExecutorServiceCalculator implements Calculator {

    //定义ExecutorService线程池
    private ExecutorService pool;
    //定义处理器内核
    private int core;

    public ExecutorServiceCalculator() {
        //cpu核心数
        this.core = Runtime.getRuntime().availableProcessors();
        //初始化线程池
        this.pool = Executors.newFixedThreadPool(core);
    }

    //实现接口的sum求和函数
    @Override
    public long sum(long[] arr){
        int part = arr.length/core;            //区间大小(不包含尾部区间)
        long total = 0L;
        for (int i = 0; i < core; i++) {        //有几个核心，我们就提交几次
            int start = i*part;
            int end = i==core-1?arr.length:(i+1)*part;
            Future<Long> future = pool.submit(new Task(arr,start,end));
            try {
                total += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    //任务内部类，继承callable
    private static class Task implements Callable<Long> {
        private long[] arr;
        private int start;
        private int end;

        public Task(long[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        //求和任务
        @Override
        public Long call() throws Exception {
            long sum = 0L;
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        }
    }
}
