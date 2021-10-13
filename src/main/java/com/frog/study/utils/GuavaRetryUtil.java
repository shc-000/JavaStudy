package com.frog.study.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;

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
}
