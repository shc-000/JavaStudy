package com.frog.study.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 各类运行时一场的getMessage信息打印
 *
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/17
 */
public class ExceptionMsgTest {

    public static void main(String[] args) {
        Map m = new HashMap<>();
        m.put("a", "2");
        try {
            //运算异常，除0
            int a = 1 / 0;
            //类型转换异常
            Integer isInt = (Integer) m.get("a");
            System.out.println(isInt);
            String[] arr = {"a"};
            //数组越界
            System.out.println(arr[2]);
            List resultList = null;
            //空指针
            if (resultList.size() > 0) {
                System.out.println("获取到的结果是：" + resultList);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e);
            System.out.println(e.getStackTrace()[0]);
            e.printStackTrace();
        }
    }
}
