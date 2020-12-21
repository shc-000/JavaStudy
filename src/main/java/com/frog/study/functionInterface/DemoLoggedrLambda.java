package com.frog.study.functionInterface;

/**
 * 对log方法进行改造
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:13 下午
 */
public class DemoLoggedrLambda{
    //这样一来,只用当级别满足的时候,进行三个字符串的拼接, 否则三个字符串将不会进行拼接
    private static void log(int level, MessageBuilder builder){
        if(level == 1){
            System.out.println(builder.buildMessage());
        }
    }
    public static void main(String[] args){
        String msg1 = "hello";
        String msg2 = "world";
        String msg3 = "java";
        log(1, () -> msg1+msg2+msg3);
    }
}
