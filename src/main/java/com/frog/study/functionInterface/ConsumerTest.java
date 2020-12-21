package com.frog.study.functionInterface;

import java.util.function.Consumer;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:17 下午
 */
//java.util.function.Consumer<T>接口和Supplier接口相反, 它不是生产一个数据,而是消费数据其数据类型有泛型决定
//        抽象方法: accept
//        Consumer接口中包含抽象方法void accept(T t)意味消费一个指定泛型数据
public class ConsumerTest {
    /*
        定义一个方法
        方法的参数传递一个字符串的姓名
        方法的参数传递Consumer接口,泛型使用String
        可以使用Consumer接口消费字符串的姓名
     */
    public static void method(String name, Consumer<String> con){
        con.accept(name);
    }
    public static void main(String[] args) {
        //调用method方法,传递字符串姓名,方法的另一个参数是Consumer接口,是一个函数式接口,所以可以传递Lambda表达式
        method("赵丽颖",(String name)->{
            //对传递的字符串进行消费
            //消费方式:直接输出字符串
            //System.out.println(name);

            //消费方式:把字符串进行反转输出
            String reName = new StringBuffer(name).reverse().toString();
            System.out.println(reName);
        });
    }



//    默认方法: andThen
//    作用: 需要两个Consumer接口,可以把两个Consumer接口组合在一起,在对数据进行消费
}
