package com.frog.study.thread;

import com.frog.study.thread.threadpool.PoolThread;
import com.frog.study.thread.threadpool.callback.AsyncCallback;
import com.frog.study.thread.threadpool.callback.LogCallback;
import com.frog.study.thread.threadpool.callback.ThreadCallback;
import com.frog.study.thread.threadpool.deliver.JavaDeliver;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/18
 */
@Slf4j
public class MainActivity {
    private int i = 0;
    private PoolThread executor;

    /**
     * 获取线程池管理器对象，统一的管理器维护所有的线程池
     * @return                      executor对象
     */
    public PoolThread getExecutor(){
        if(executor ==null){
            executor = PoolThread.ThreadBuilder
                    .createFixed(5)
                    .setPriority(Thread.MAX_PRIORITY)
                    .setCallback(new LogCallback())
                    .build();
        }
        return executor;
    }
    private void startThread1() {
        PoolThread executor = getExecutor();
        executor.setName("最简单的线程调用方式");
        executor.setDeliver(new JavaDeliver());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                log.info("PoolThreadMainActivity","最简单的线程调用方式");
            }
        });
    }


    private void startThread2() {
        executor = getExecutor();
        executor.setName("异步回调");
        executor.setDelay(2, TimeUnit.SECONDS);
        // 启动异步任务
        executor.async(new Callable<Login>(){
            @Override
            public Login call() throws Exception {
                log.info("PoolThreadAsyncCallback","耗时操作");
                Thread.sleep(5000);
                // 做一些操作
                return null;
            }
        }, new AsyncCallback<Login>() {
            @Override
            public void onSuccess(Login user) {
                log.info("PoolThreadAsyncCallback","成功");
            }

            @Override
            public void onFailed(Throwable t) {
                log.info("PoolThreadAsyncCallback","失败");
            }

            @Override
            public void onStart(String threadName) {
                log.info("PoolThreadAsyncCallback","开始");
            }
        });
    }


    private void startThread3() {
        PoolThread executor = getExecutor();
        executor.setName("延迟时间执行任务");
        executor.setDelay(2, TimeUnit.SECONDS);
        executor.setDeliver(new JavaDeliver());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                log.info("PoolThreadMainActivity","延迟时间执行任务");
            }
        });
    }


    private void startThread4() {
        PoolThread executor = getExecutor();
        //设置为当前的任务设置线程名
        executor.setName("延迟时间执行任务");
        //设置当前任务的延迟时间
        executor.setDelay(2, TimeUnit.SECONDS);
        //设置当前任务的线程传递
        executor.setDeliver(new JavaDeliver());
        executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("PoolThreadstartThread4","startThread4---call");
                Thread.sleep(2000);
                String str = "小杨逗比";
                return str;
            }
        });
        executor.setCallback(new ThreadCallback() {
            @Override
            public void onError(String threadName, Throwable t) {
                log.info("PoolThreadstartThread4","startThread4---onError");
            }

            @Override
            public void onCompleted(String threadName) {
                log.info("PoolThreadstartThread4","startThread4---onCompleted");
            }

            @Override
            public void onStart(String threadName) {
                log.info("PoolThreadstartThread4","startThread4---onStart");
            }
        });
    }



    private void startThread5() {
        PoolThread executor = getExecutor();
        //设置为当前的任务设置线程名
        executor.setName("延迟时间执行任务");
        //设置当前任务的延迟时间
        executor.setDelay(2, TimeUnit.SECONDS);
        //设置当前任务的线程传递
        executor.setDeliver(new JavaDeliver());
        Future<String> submit = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("PoolThreadstartThread5","startThread5---call");
                Thread.sleep(2000);
                String str = "小杨逗比";
                return str;
            }
        });
        try {
            String result = submit.get();
            log.info("PoolThreadstartThread5","startThread5-----"+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
