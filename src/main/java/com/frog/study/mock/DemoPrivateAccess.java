package com.frog.study.mock;

import java.util.List;

/**
 * 演示私有成员访问功能
 * Demonstrate private member access functionality
 */
public class DemoPrivateAccess {

    /**
     * a private static field
     */
    public static int staticCount;
    /**
     * a private member field
     */
    public int count;
    /**
     * a constant field
     */
    public Double pi = 3.14;

    /**
     * private static method
     */
    public static String privateStaticFunc(String str, int i) {
        return str + " + " + i;
    }

    /**
     * private member method
     */
    public String privateFunc(List<String> list, String str, int i) {
        return list.stream().reduce((a, s) -> a + s).orElse("") + " + " + str + " + " + i;
    }

}
