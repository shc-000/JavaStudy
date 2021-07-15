package com.frog.study.lambda.consumer;

import com.frog.study.Student;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/15
 */
@FunctionalInterface
public interface CalcFunction <T>  {
    Student calc(T value);
}
