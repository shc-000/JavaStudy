package com.frog.study.lambda.collector;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/7/15 9:16 下午
 */
@Slf4j
public class MutiCalcCollector implements Collector<OrderDto, Map<String, MutiCalcModel>, Map<String, MutiCalcModel>> {

    @Override
    public Supplier<Map<String, MutiCalcModel>> supplier() {
        log.info("supplier invoke ....");
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<String, MutiCalcModel>, OrderDto> accumulator() {
        log.info("accumulator invoke ....");

        return (map, orderDto) -> {
            String status = orderDto.getStatus();
            int orderQty = orderDto.getOrderQty();
            int warningOrderQty = orderDto.getWarningOrderQty();
            if (map.containsKey(status)) {
                MutiCalcModel model = map.get(status);
                model.setOrderSum(model.getOrderSum() + 1);
                model.setOrderQtySum(model.getOrderQtySum() + orderQty);
                model.setWarningOrderQtySum(model.getWarningOrderQtySum() + warningOrderQty);
                map.put(status, model);
            } else {
                MutiCalcModel model = new MutiCalcModel();
                model.setOrderSum(1);
                model.setWarningOrderQtySum(warningOrderQty);
                model.setOrderQtySum(orderQty);
                map.put(status, model);
            }
        };
    }

    @Override
    public BinaryOperator<Map<String, MutiCalcModel>> combiner() {
        log.info("no combiner invoke ....");
        return null;
    }

    @Override
    public Function<Map<String, MutiCalcModel>, Map<String, MutiCalcModel>> finisher() {
        log.info("finisher invoke ....");
        return (a) -> a;
    }

    @Override
    public Set<Characteristics> characteristics() {
        log.info("characteristics invoke ....");
        return Collections.unmodifiableSet(
                /*
                // finisher 不会执行
                EnumSet.of(
                        Characteristics.UNORDERED,
                        Characteristics.CONCURRENT,
                        Characteristics.IDENTITY_FINISH
                )
                */
                // finisher 会执行
                EnumSet.of(
                        Characteristics.UNORDERED,
                        Characteristics.CONCURRENT
                )
        );
    }
}
