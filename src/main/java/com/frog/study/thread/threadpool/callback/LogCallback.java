package com.frog.study.thread.threadpool.callback;

import lombok.extern.slf4j.Slf4j;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/18
 */
@Slf4j
public class LogCallback implements ThreadCallback {
    private final String TAG = "LogCallback";

    @Override
    public void onError(String name, Throwable t) {
        log.info(TAG, "LogCallback"+"------onError"+"-----"+name+"----"+Thread.currentThread()+"----"+t.getMessage());
    }

    @Override
    public void onCompleted(String name) {
        log.info(TAG, "LogCallback"+"------onCompleted"+"-----"+name+"----"+Thread.currentThread());
    }

    @Override
    public void onStart(String name) {
        log.info(TAG, "LogCallback"+"------onStart"+"-----"+name+"----"+Thread.currentThread());
    }
}
