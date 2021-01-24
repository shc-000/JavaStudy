package com.frog.study.simple;

import com.frog.study.Student;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
//        String aa = "ABS_HH_123";
//        String newName = aa.substring(aa.lastIndexOf("_")+1);
//        System.out.println(newName);

        // 随便虚拟一个日期
//        LocalDate now = LocalDate.now().plusDays(10);
        LocalDate now = LocalDate.now();
        System.out.println("当前日期: " + now + " " + now.getDayOfWeek());
        // 求这个日期上一周的周一、周日
        LocalDate todayOfLastWeek = now.minusDays(7);
        LocalDate todayOfNextWeek = now.plusDays(7);

//        LocalDate monday = todayOfLastWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
//        LocalDate sunday = todayOfLastWeek.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
//        System.out.println("当前日期：" + now + " 上一周的周一：" + monday + " " + monday.getDayOfWeek());
//        System.out.println("当前日期：" + now + " 上一周的周日：" + sunday + " " + sunday.getDayOfWeek());

        LocalDate nextMonday = todayOfNextWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
        System.out.println("当前日期：" + now + " 下一周的周一：" + nextMonday + " " + nextMonday.getDayOfWeek());

    }
}
