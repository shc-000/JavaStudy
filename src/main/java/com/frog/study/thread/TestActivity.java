package com.frog.study.thread;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/18
 */
@Slf4j
public class TestActivity {
    int number = 200;

    /**
     * 最普通创建线程的方式
     */
    private void createThread() {
        for (int i = 1; i <= number; i++) {
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    log.info("潇湘剑雨Thread", "线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 由于newFixedThreadPool只有核心线程，并且这些线程都不会被回收，也就是它能够更快速的响应外界请求
     */
    private void newFixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= number; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    log.info("潇湘剑雨newFixedThreadPool", "线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void newFixedThreadPool1() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= number; i++) {
            final int index = i;
            fixedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    log.info("潇湘剑雨newFixedThreadPool", "线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private void newSingleThreadExecutor() {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= number; i++) {
            final int index = i;
            singleThreadPool.execute(new Runnable() {
//                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    log.info("潇湘剑雨 newSingleThreadExecutor", "线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void newSingleThreadExecutor1() {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= number; i++) {
            final int index = i;
            singleThreadPool.submit(new Runnable() {
//                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    log.info("潇湘剑雨 newSingleThreadExecutor", "线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    /**
     * 缓存线程池，
     */
    private void newCachedThreadPool() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= number; i++) {
            final int index = i;
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    log.info("潇湘剑雨newCachedThreadPool", "线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        long time =  500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void newCachedThreadPool1() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= number; i++) {
            final int index = i;
            cachedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    log.info("潇湘剑雨newCachedThreadPool", "线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        long time =  500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private void newScheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        //延迟2秒后执行该任务
        scheduledThreadPool.schedule(new Runnable() {
//            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                log.info("潇湘剑雨newScheduledThreadPool", "线程：" + threadName + ",正在执行");
            }
        }, 2, TimeUnit.SECONDS);
        //延迟1秒后，每隔2秒执行一次该任务
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                log.info("潇湘剑雨", "线程：" + threadName + ",正在执行");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    private void test(){
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                return null;
            }
        };
    }

}
