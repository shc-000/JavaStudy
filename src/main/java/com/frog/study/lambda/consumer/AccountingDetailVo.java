package com.frog.study.lambda.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountingDetailVo implements Serializable {


    private static final long serialVersionUID = 6226733035919673733L;

    private Long amtType;

    private Long acctNo;

    private Long acctType;

    private Long custNo;

    private String balDir;

    private BigDecimal amt;

    /**
     * 根据资金方向取得金额
     */
    public double getAmtByBalDirToDouble() {
        //+—*/的枚举
        return this.getBalDir().equals(BalDirEnum.ADD.getValue()) ? this.getAmt().doubleValue() : this.getAmt().negate().doubleValue();
    }

    public BigDecimal getAmtByBalDir() {
        return this.getBalDir().equals(BalDirEnum.ADD.getValue()) ? this.getAmt() : this.getAmt().negate();
    }

}
