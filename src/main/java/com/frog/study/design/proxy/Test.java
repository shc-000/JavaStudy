package com.frog.study.design.proxy;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/5/24 9:03 下午
 */
public class Test {
    public static void main(String[] args) {
        Subject subject = new RealSubject();
        DemoInvokerHandler invokerHandler = new DemoInvokerHandler(subject);
        // 获取代理对R象
        Subject proxy = (Subject) invokerHandler.getProxy();
        // 调用代理对象的方法，它会调用DemoInvokerHandler.invoke()方法
        String msg = proxy.call("shc");
        System.out.println("end");
    }
}
