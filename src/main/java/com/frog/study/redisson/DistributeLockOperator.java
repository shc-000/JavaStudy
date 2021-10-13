package com.frog.study.redisson;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.remoting.RemoteAccessException;

import java.util.concurrent.TimeUnit;

/**
 * @author shaohaichao
 * @since 2021/10/12
 */
@Slf4j
public class DistributeLockOperator {

    /**
     * 分布式锁key
     */
    public static final String DISTRIBUTE_LOCK_KEY = "distribute_lock";

    /**
     * 加锁（+重试机制）
     *
     * @return boolean
     */
    public boolean tryGetLock(RLock disLock, long sleepTime, int attemptNumber, long waitTime, String methodName) {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                //网络异常重试
                .retryIfExceptionOfType(RemoteAccessException.class)
                //拿不到锁重试
                .retryIfResult(getLock -> !getLock)
                //设置等待间隔时间30
                .withWaitStrategy(WaitStrategies.fixedWait(sleepTime, TimeUnit.SECONDS))
                //设置最大重试次数3
                .withStopStrategy(StopStrategies.stopAfterAttempt(attemptNumber))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("nebula-intg-backend: {}:try get lock ..【{}】", methodName, attempt.getAttemptNumber());
                    }
                })
                .build();

        boolean isLock;
        try {
            //尝试获取分布式锁，等待30s,waitTime=30
            isLock = retryer.call(() -> disLock.tryLock(waitTime, TimeUnit.SECONDS));
            log.info("nebula-intg-backend: {}:get lock success", methodName);
        } catch (Exception e) {
            log.error("nebula-intg-backend: {}:get lock fail", methodName, e);
            return false;
        }
        return isLock;
    }

    /**
     * 释放锁
     */
    public void releaseLock(RLock disLock, String methodName) {
        try {
            // 是否还是锁定状态
            if (disLock.isLocked()) {
                //当前执行线程的锁
                if (disLock.isHeldByCurrentThread()) {
                    disLock.unlock();
                }
            }
            log.info("nebula-intg-backend: {}:release lock...", methodName);
        } catch (Exception e) {
            log.warn("nebula-intg-backend: {}:release lock fail", methodName, e);
        }
    }

    public void test() {
        RedissonClient redissonClient = RedssonConfig.redisson();
        // 设置锁定资源名称
        RLock disLock = redissonClient.getLock(DISTRIBUTE_LOCK_KEY);
        try {
            boolean isLock = tryGetLock(disLock, 10L, 3, 1000, "test()");
            if (isLock) {
                //do something...
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseLock(disLock, "test()");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RedissonClient redissonClient = RedssonConfig.redisson();
        // 设置锁定资源名称
        RLock disLock = redissonClient.getLock(DISTRIBUTE_LOCK_KEY);
        //尝试获取分布式锁，等待10s
        try {
            long id = Thread.currentThread().getId();
            System.out.println("线程ID" + id);
            boolean flag = disLock.tryLock(10L,10L, TimeUnit.MINUTES);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //需要使用相同的disLock对象才能加锁后释放锁
            if (disLock.isLocked()) {
                //当前执行线程的锁
                if (disLock.isHeldByCurrentThread()) {
                    disLock.unlock();
                } else {
                    System.out.println("not right");
                }
            }
        }
    }


}
