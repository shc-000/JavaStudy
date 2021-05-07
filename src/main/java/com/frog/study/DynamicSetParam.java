package com.frog.study;


import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/4/8
 */
@Data
public class DynamicSetParam {

    public static void main(String[] args) {
        StuParam stuParam = new StuParam();
        stuParam.setObjectProperties("param1",11);
        stuParam.setObjectProperties("param2",22);
        stuParam.setObjectProperties("param3",33);
        System.out.println(stuParam.toString());
    }
}
