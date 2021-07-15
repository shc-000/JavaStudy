package com.frog.study.lambda.collector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/7/15 9:36 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private int orderQty;
    private String status;
    private int warningOrderQty;
}
