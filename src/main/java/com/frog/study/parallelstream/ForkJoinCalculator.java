package com.frog.study.parallelstream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/21
 */
//ForkJoinCalculator类(用于测试forkjoin线程池)
public class ForkJoinCalculator implements Calculator {

    private ForkJoinPool pool;

    public ForkJoinCalculator() {
        //也可以使用公有线程池ForkJoinPool.commonPool()
        this.pool = new ForkJoinPool();
    }

    @Override
    public long sum(long[] arr) {
        //执行总任务
        return pool.invoke(new Task(arr,0,arr.length));
    }

    //任务内部类, 继承RecursiveTask<Long>
    private static class Task extends RecursiveTask<Long> {

        private static final long THRESHOLD = 10L;

        private long[] arr;
        private int start;
        private int end;

        public Task(long[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        //总任务
        @Override
        protected Long compute() {
            //长度小于临界值,就不再拆分任务
            int len = end - start;
            if (len <= THRESHOLD){
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += arr[i];
                }
                return sum;
            }else {
                //计算中点坐标
                int middle = (start + end)/2;
                //拆分2个子任务
                Task leftTask = new Task(arr,start,middle);
                Task rightTask = new Task(arr,middle,end);
                //执行子任务
                leftTask.fork();
                rightTask.fork();
                //结果合并
                return leftTask.join()+rightTask.join();
            }
        }
    }
}
