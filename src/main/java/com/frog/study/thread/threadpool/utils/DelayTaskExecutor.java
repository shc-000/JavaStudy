package com.frog.study.thread.threadpool.utils;

import java.util.concurrent.*;

/**
 * 使用核心线程池启动延迟任务的类
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/18
 */
@SuppressWarnings("ALL")
public final class DelayTaskExecutor {

    private ScheduledExecutorService dispatcher;

    /**
     * 单利模式，创建线程池对象
     */
    private static DelayTaskExecutor instance = new DelayTaskExecutor();

    private DelayTaskExecutor() {
        dispatcher = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("Yc_Delay-Task-Dispatcher");
                thread.setPriority(Thread.MAX_PRIORITY);
                return thread;
            }
        });
    }

    public static DelayTaskExecutor get() {
        return instance;
    }

    /**
     * 启动
     * @param delay                     延迟执行的时间，注意默认单位是TimeUnit.MILLISECONDS
     * @param pool                      pool线程池
     * @param task                      runnable
     */
    public void postDelay(long delay, final ExecutorService pool, final Runnable task) {
        if (delay == 0) {
            //如果时间是0，那么普通开启
            pool.execute(task);
            return;
        }

        //延时操作
        dispatcher.schedule(new Runnable() {
            @Override
            public void run() {
                //在将来的某个时间执行给定的命令。该命令可以在新线程、池线程或调用线程中执行
                pool.execute(task);
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

}
