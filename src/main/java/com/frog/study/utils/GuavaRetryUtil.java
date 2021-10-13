package com.frog.study.utils;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;

import java.util.concurrent.TimeUnit;

/**
 * @author shaohaichao
 * @since 2021/10/11
 */
@Slf4j
public class GuavaRetryUtil {
    /**
     * 重试方法
     */
    public static boolean retryTask(String param) {
        log.info("收到请求参数:{}", param);
        int max = 11, min = 1;
        int i = (int) (Math.random() * (max - min) + min);
        log.info("随机生成的数:{}", i);
        if (i < 2) {
            log.info("为0,抛出参数异常.");
            throw new IllegalArgumentException("参数异常");
        } else if (i < 5) {
            log.info("为1,返回true.");
            return false;
        } else if (i < 7) {
            log.info("为2,返回false.");
            return false;
        } else {
            //为其他
            log.info("大于2,抛出自定义异常.");
            throw new RemoteAccessException("大于2,抛出自定义异常");
        }
    }

    public void test() {
        // RetryerBuilder 构建重试实例 retryer,可以设置重试源且可以支持多个重试源，可以配置重试次数或重试超时时间，以及可以配置等待时间间隔
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                //设置异常重试源
                .retryIfExceptionOfType(RemoteAccessException.class)
                //设置根据结果重试
                .retryIfResult(res -> res == false)
                //设置等待间隔时间
                .withWaitStrategy(WaitStrategies.fixedWait(3, TimeUnit.SECONDS))
                //设置最大重试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("try get lock ..尝试次数：【{}】", attempt.getAttemptNumber());
                    }
                })
                .build();
        try {
            retryer.call(() -> retryTask("abc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
