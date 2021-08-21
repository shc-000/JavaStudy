package com.frog.study.ifelse;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/08/20
 */
@OrderHandlerType(source = "pc")
public class PCOrderHandler implements OrderHandler {
    @Override
    public void handle(Order order) {

        System.out.println("处理PC端订单");
    }
}
