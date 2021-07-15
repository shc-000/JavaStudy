package com.frog.study.lambda.consumer;

import java.util.function.IntConsumer;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/15
 */
public class MyConsumer implements IntConsumer {
    private long orderSum;
    private long orderQtySum;
    private int warningOrderSum;
    private int warningOrderQtySum;
    private String status;

    @Override
    public void accept(int value) {

    }
}
