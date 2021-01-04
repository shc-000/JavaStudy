package com.frog.study.simple;

import com.frog.study.Student;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/28
 */
public class Test {
    private static List<Student> buildStudentList() {
        return Arrays.asList(new Student("张三", 18, "男", "清华"),
                new Student("李斯", 19, "男", "清华"),
                new Student("李四", 18, "男", "清华"),
                new Student("王五", 17, "男", "清华"),
                new Student("昭君", 19, "女", "清华"),
                new Student("貂蝉", 18, "女", "北大"),
                new Student("西施", 16, "女", "南开"));
    }

    public static void main(String[] args) {
//        List<Student> students = buildStudentList();
//        students.forEach(student -> {
//            student.setAge(66);
//        });
//        System.out.println(students);

        String aa = "ABS_HH_123";
        String newName = aa.substring(aa.lastIndexOf("_")+1);
        System.out.println(newName);
    }
}
