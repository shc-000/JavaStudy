package com.frog.study.design.proxy;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/5/24 8:54 下午
 */
public class RealSubject implements Subject {

    @Override
    public String call(String name) {
        System.out.println(name + "----");
        return name;
    }
}
