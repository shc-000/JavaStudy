package com.frog.study;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/4/8
 */
@Data
public class StuParam{

    private Integer param1;

    private Integer param2;

    private Integer param3;

    void setObjectProperties(String paramName, Integer value) {
        try {
            Class<?> objectClass = this.getClass();
            Field param = objectClass.getDeclaredField(paramName);
            param.setAccessible(true);
            param.set(this, value);
        } catch (Exception e) {
            System.out.println("字段值错误或无此属性访问权限");
        }
    }
}

