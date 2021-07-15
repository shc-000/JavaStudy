package com.frog.study.exception;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/02
 */
public class InterruptedExceptionTest {
    public static void main(String[] args) {
        try {
            testName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void testName() throws Exception {
        // 被中断的线程
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                    System.out.println("继续吗。。。111");
                } catch (InterruptedException e) {
                    // 当调用t.interrupt(),则会刨除此异常
                    Thread.currentThread().interrupt();
                    System.out.println("继续。。。222");
//                    e.printStackTrace();
                }
            }
        };
        // 去中断t的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                t.interrupt();
            }
        }).start();

        t.start();
        t.join();
    }
}
