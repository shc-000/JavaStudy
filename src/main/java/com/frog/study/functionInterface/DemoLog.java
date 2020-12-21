package com.frog.study.functionInterface;

/**
 * 函数式编程
 * 性能浪费的日志案例, 打印输出
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:11 下午
 */
public class DemoLog{
//    这段代码存在问题：无论级别是否满足要求，作为 log 方法的第二个参数，三个字符串一定会首先被拼接并传入方 法内，
//    然后才会进行级别判断。如果级别不符合要求，那么字符串的拼接操作就白做了，存在性能浪费。
    public static void main(String[] args){
        String msg1 = "hello";
        String msg2 = "world";
        String msg3 = "java";
        log(1, msg1+msg2+msg3);
    }
    public static void log(int level, String msg){
        if(level==1){
            System.out.println(msg);
        }
    }
}
