package com.frog.study.functionInterface;

import java.util.function.Consumer;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:20 下午
 */
//默认方法: andThen
//作用: 需要两个Consumer接口,可以把两个Consumer接口组合在一起,在对数据进行消费
public class Demo02Consumer {
    //定义一个方法, 方法的参数传入一个字符串和两个Consumer接口. Consumer接口的泛型使用字符串
    public static void method(String s, Consumer<String> con1, Consumer<String> con2) {
        // 使用andThen方法, 把两个Consumer接口连接在一起,在消费数据
        con1.andThen(con2).accept(s);
        // con1连接con2,先执行con1消费数据, 在执行con2消费数据, 谁在前面先执行消费
    }

    public static void main(String[] args) {
        //调用method方法, 传递一个字符串, 两个lambda表达式
        method("Hello",
                (t) -> {
                    System.out.println(t.toUpperCase());
                },
                (t) -> {
                    System.out.println(t.toLowerCase());
                });
    }
}
