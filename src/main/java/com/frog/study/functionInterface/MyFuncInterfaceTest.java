package com.frog.study.functionInterface;

import org.junit.Test;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
public class MyFuncInterfaceTest {
    @Test
    public void testFunc() {
        //    作为参数传递Lambda表达式：
        String trimStr = strHandler("\t\t 春江花月夜       ", (str) -> str.trim());
        System.out.println(trimStr);
        String upperStr = strHandler("abcdefg", (str) -> str.toUpperCase());
        System.out.println(upperStr);
        String newStr = strHandler("天门中断楚江开", (str) -> str.substring(2, 5));
        System.out.println(newStr);
    }

    public String strHandler(String str, MyFuncInterface<String> mf) {
        return mf.getValue(str);
    }


}
