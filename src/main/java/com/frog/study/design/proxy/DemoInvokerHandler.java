package com.frog.study.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/5/24 8:55 下午
 */
public class DemoInvokerHandler implements InvocationHandler {
    // 真正的业务对象，也就是RealSubject对象
    private Object targetObj;

    public DemoInvokerHandler(Object targetObj) {
        this.targetObj = targetObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("do before...");
        // 在执行业务逻辑之前的预处理逻辑
        Object result = method.invoke(targetObj, args);
        // 在执行业务逻辑之后的后置处理逻辑
        System.out.println("do after...");
        return result;
    }

    public Object getProxy() {
        // 创建代理对象
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), this);
    }
}
