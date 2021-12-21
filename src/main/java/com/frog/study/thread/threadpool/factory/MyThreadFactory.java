package com.frog.study.thread.threadpool.factory;

import lombok.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * 默认Thread工厂
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/18
 */
public class MyThreadFactory implements ThreadFactory {

    /**
     * ThreadFactory
     * ThreadFactory是一个接口，里面只有一个newThread方法
     * 线程工厂，为线程池提供新线程的创建
     */

    private int priority;

    /**
     * 构造方法，默认为优先级是：Thread.NORM_PRIORITY
     */
    public MyThreadFactory() {
        this.priority = Thread.NORM_PRIORITY;
    }

    /**
     * 构造方法
     * @param priority                  优先级
     */
    public MyThreadFactory(int priority) {
        this.priority = priority;
    }


    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        // 创建线程
        Thread thread = new Thread(runnable);
        // 设置线程优先级
        thread.setPriority(priority);
        return thread;
    }

}
