package com.frog.study.ifelse;

import com.google.common.collect.Lists;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/08/20
 */
public class Test {
    public static void main(String[] args) {
        String type = AnnotationUtils.findAnnotation(MobileOrderHandler.class, OrderHandlerType.class).source();

//        OrderService orderService = new OrderService();
//        OrderHandler mobile = new MobileOrderHandler();
//        OrderHandler pc = new PCOrderHandler();
//        List<OrderHandler> orderHandlers = Lists.newArrayList();
//        orderHandlers.add(mobile);
//        orderHandlers.add(pc);
//        orderService.setOrderHandleMap(orderHandlers);
//        Order order = new Order();
//        order.setSource("mobile");
//        order.setCode("001");
//        order.setPayMethod("phone");
//        orderService.doOrderService(order);
    }
}
