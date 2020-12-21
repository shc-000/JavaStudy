package com.frog.study.functionInterface;

/**
 * 自定义函数式接口
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:10 下午
 */
public class Demo{
    //使用自定义的函数式接口作为方法参数
    private static void doSomething(MyfunctionInterface inter){
        inter.myMethod(); //调用自定义的函数式接口
    }
    public static void main(String[] args){
        //调用使用函数式接口的方法
        doSomething(() -> System.out.println("lambda执行了"));
    }
}
