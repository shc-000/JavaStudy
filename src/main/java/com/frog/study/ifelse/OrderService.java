package com.frog.study.ifelse;

import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/08/20
 */
public class OrderService {

    /*原始方式
    public void orderService(Order order) {
        if(order.getSource().equals("pc")){
            // 处理pc端订单的逻辑
        }else if(order.getSource().equals("mobile")){
            // 处理移动端订单的逻辑
        }else {
            // 其他逻辑
        }
    }*/

    //进化版本1
    private Map<String, OrderHandler> orderHandleMap;

    public void setOrderHandleMap(List<OrderHandler> orderHandlers) {

        // 注入各种类型的订单处理类
        orderHandleMap = orderHandlers.stream().collect(
                Collectors.toMap(orderHandler -> AnnotationUtils.findAnnotation(orderHandler.getClass(), OrderHandlerType.class).source(),
                                 v -> v, (v1, v2) -> v1));
    }

    public void doOrderService(Order order) {
        // ...一些前置处理

        // 通过订单来源确定对应的handler
        OrderHandler orderHandler = orderHandleMap.get(order.getSource());
        orderHandler.handle(order);

        // ...一些后置处理
    }
}
