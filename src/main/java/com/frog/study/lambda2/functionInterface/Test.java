package com.frog.study.lambda2.functionInterface;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/1/24
 */
public class Test {
    public interface MyFunc<T> {
        T getValue(T t);
    }

    public static String toUpperString(MyFunc<String> myFunc, String str) {
        return myFunc.getValue(str);
    }

    public static void main(String[] args) {
        String newStr = toUpperString((str) -> str.toUpperCase(), "abc");
        System.out.println(newStr);
    }
}
