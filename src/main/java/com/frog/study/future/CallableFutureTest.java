package com.frog.study.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/16 9:34 下午
 */
public class CallableFutureTest {
    private String aa;
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Callable<Integer> calculateCallable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //这里模拟一个耗时的操作，如从数据库取大量数据。。。
                Thread.sleep(2000);
                int result = 1 + 2;
                return result;
            }
        };

        FutureTask<Integer> calculateFutureTask = new FutureTask<>(calculateCallable);

        Thread t1 = new Thread(calculateFutureTask);
        t1.start();
        //现在加入Thread运行的是一个模拟远程调用耗时的服务，并且依赖他的计算结果

        try {
            //模拟耗时任务，主线程做自己的事情，体现多线程的优势
            Thread.sleep(3000);
            int a = 3 + 3;
            Integer result = calculateFutureTask.get();
//            get方法：获取计算结果（如果还没计算完，也是必须等待的，阻塞）
//            cancel方法：还没计算完，可以取消计算过程
//            isDone方法：判断是否计算完
//            isCancelled方法：判断计算是否被取消
            System.out.println("result = " + (a + result));//模拟主线程依赖子线程的运行结果
            long endTime = System.currentTimeMillis();
            System.out.println("time =" + (endTime - startTime) + "ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
