package com.frog.study.ifelse;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/08/20
 */
@Data
public class Order {
    /**
     * 订单来源
     */
    private String source;
    /**
     * 支付方式
     */
    private String payMethod;
    /**
     * 订单编号
     */
    private String code;
    /**
     * 订单金额
     */
    private BigDecimal amount;
    // ...其他的一些字段
}
