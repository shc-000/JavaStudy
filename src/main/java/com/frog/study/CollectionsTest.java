package com.frog.study;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/15 9:22 下午
 */
public class CollectionsTest {
    private List<Student> buildStudentList(){
        return Arrays.asList(new Student("张三",18,"男","清华"),
                new Student("李斯",19,"男","清华"),
                new Student("貂蝉",18,"女","北大"),
                new Student("西施",16,"女","南开"));
    }

    public static void main(String[] args) {
        CollectionsTest collectionsTest = new CollectionsTest();
        List<Student> students = collectionsTest.buildStudentList();
        collectionsTest.sort(students);
    }

    private void sort(List<Student> students){
        //Collections.sort(students, (s1, s2) -> Integer.compare(s1.getAge(),s2.getAge()));
        //students默认实现的List接口中在Java8增加了default的sort方法，所以可以在下面直接使用
        students.forEach(student -> System.out.println(student.toString()));
        System.out.println("-------------------");
        //按照年龄从小到大排序
        students.sort((s1, s2) -> Integer.compare(s1.getAge(), s2.getAge()));
        //上下等价
        //正序
        students.sort(Comparator.comparingInt(Student::getAge));
        //倒叙
        students.sort(Comparator.comparingInt(Student::getAge).reversed());
        students.forEach(student -> System.out.println(student.toString()));
        System.out.println("-------------------");
        //按照年龄从大到小排序
        students.sort((s1, s2) -> Integer.compare(s2.getAge(), s1.getAge()));
        students.forEach(student -> System.out.println(student.toString()));
        System.out.println("-------------------");
        //对集合倒叙输出
        Collections.reverse(students);
        students.forEach(student -> System.out.println(student.toString()));

        System.out.println("-------------------");
        //stream
        students = students.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        students.forEach(student -> System.out.println(student.toString()));
        System.out.println("-------------------");
        students =  students.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        students.forEach(student -> System.out.println(student.toString()));
    }

}
