package com.frog.study.lambda.consumer;

import java.math.BigDecimal;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/15
 */
@FunctionalInterface
public interface ToBigDecimalFunction <T> {

    BigDecimal applyAsBigDecimal(T value);

}
