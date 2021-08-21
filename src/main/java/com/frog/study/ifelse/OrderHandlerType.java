package com.frog.study.ifelse;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/08/20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderHandlerType {

    String source();
}
