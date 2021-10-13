package com.shc.test;

import com.frog.study.utils.GuavaRetryUtil;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import org.springframework.remoting.RemoteAccessException;

import java.util.concurrent.TimeUnit;

/**
 * @author shaohaichao
 * @since 2021/10/11
 */
public class GuavaRetryTest {
    @org.junit.Test
    public void fun01(){
        // RetryerBuilder 构建重试实例 retryer,可以设置重试源且可以支持多个重试源，可以配置重试次数或重试超时时间，以及可以配置等待时间间隔
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean> newBuilder()
                .retryIfExceptionOfType(RemoteAccessException.class)//设置异常重试源
                .retryIfResult(res-> res==false)  //设置根据结果重试
                .withWaitStrategy(WaitStrategies.fixedWait(3, TimeUnit.SECONDS)) //设置等待间隔时间
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) //设置最大重试次数
                .build();

        try {
           boolean result = retryer.call(() -> GuavaRetryUtil.retryTask("abc"));
            System.out.println(result + "输出结果");
        } catch (Exception e) {
            System.out.println("获取锁失败");
        }
        System.out.println(123);
    }
}
