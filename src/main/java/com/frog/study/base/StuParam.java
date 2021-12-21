package com.frog.study.base;

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

    //动态设置属性值
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

    public static void main(String[] args) {
        StuParam stuParam = new StuParam();
        stuParam.setObjectProperties("param1",11);
        stuParam.setObjectProperties("param2",22);
        stuParam.setObjectProperties("param3",33);
        System.out.println(stuParam.toString());
    }
}

