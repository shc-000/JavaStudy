package com.frog.study.lambda.collector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/7/15 9:14 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutiCalcModel {
    /**
     * 改状态的count
     */
    private int orderSum;
    /**
     * orderQty的累加
     */
    private int orderQtySum;
    /**
     * warningOrder的count
     */
    private int warningOrderSum;
    /**
     * warningOrderQty的累加
     */
    private int warningOrderQtySum;
}
