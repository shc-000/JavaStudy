package com.frog.study.lambda.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/15
 */
public class ListGroupTest2 {
    public static void main(String[] args) {

        List<AccountingDetailVo> voList = new ArrayList<>();
        AccountingDetailVo vo1 = AccountingDetailVo.builder().acctNo(1L).balDir("-").amt(BigDecimal.valueOf(500.22)).build();
        AccountingDetailVo vo2 = AccountingDetailVo.builder().acctNo(2L).balDir("+").amt(BigDecimal.valueOf(500.33)).build();
        AccountingDetailVo vo3 = AccountingDetailVo.builder().acctNo(1L).balDir("-").amt(BigDecimal.valueOf(500.44)).build();
        AccountingDetailVo vo4 = AccountingDetailVo.builder().acctNo(2L).balDir("+").amt(BigDecimal.valueOf(500.55)).build();
        AccountingDetailVo vo5 = AccountingDetailVo.builder().acctNo(3L).balDir("+").amt(BigDecimal.valueOf(500.66)).build();
        voList.add(vo1);
        voList.add(vo2);
        voList.add(vo3);
        voList.add(vo4);
        voList.add(vo5);
        TreeMap<Long, BigDecimal> resultList333 = voList.stream().collect(
                Collectors.groupingBy(AccountingDetailVo::getAcctNo, TreeMap::new, CollectorsUtils.summingBigDecimal(AccountingDetailVo::getAmtByBalDir)));

        System.out.println(JSON.toJSONString(resultList333, SerializerFeature.PrettyFormat));

    }
}
