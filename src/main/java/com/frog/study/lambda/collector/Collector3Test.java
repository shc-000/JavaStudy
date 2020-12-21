package com.frog.study.lambda.collector;

import com.frog.study.Student;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/19 4:21 下午
 */
public class Collector3Test {
    public static void main(String[] args) {
        toConcurrentMapWithBinaryOperator(buildStudentList());
        toConcurrentMap(buildStudentList());

        summarizingDouble(buildStudentList());

        reducingBinaryOperatorAndIdentiy(buildStudentList());
        reducingBinaryOperator(buildStudentList());
        partitioningByWithPredicate(buildStudentList());

        joiningWithDelimiterAndPrefixAndSuffix(buildStudentList());
        maxBy(buildStudentList());
        minBy(buildStudentList());
    }

    private static List<Student> buildStudentList() {
        return Arrays.asList(new Student("张三", 18, "男", "清华"),
                new Student("李斯", 19, "男", "清华"),
                new Student("李四", 18, "男", "清华"),
                new Student("王五", 17, "男", "清华"),
                new Student("昭君", 19, "女", "清华"),
                new Student("貂蝉", 18, "女", "北大"),
                new Student("西施", 16, "女", "南开"));
    }

    private static void toConcurrentMapWithBinaryOperator(List<Student> students) {
        //统计每个学校的人数
        Optional.ofNullable(students.stream().collect(Collectors.toConcurrentMap(Student::getSchool, v -> 1L, (a, b) -> a + b))).ifPresent(System.out::println);
    }

    private static void toConcurrentMap(List<Student> students) {
        //key冲突则覆盖(key1, key2) -> key2
        Optional.ofNullable(students.stream().collect(Collectors.toConcurrentMap(Student::getSchool, Student::getAge, (key1, key2) -> key2))).ifPresent(System.out::println);
    }

    private static void toCollection(List<Student> students) {
        students.stream().collect(Collectors.toCollection(LinkedList::new));
    }

    private static void summarizingDouble(List<Student> students) {
        //返回数据统计信息
        Optional.ofNullable(students.stream().collect(Collectors.summarizingDouble(Student::getAge))).ifPresent(System.out::println);
    }

    private static void reducingBinaryOperatorAndIdentiy(List<Student> students) {
        //年龄总和
        Integer result = students.stream().map(Student::getAge).collect(Collectors.reducing(0, (d1, d2) -> d1 + d2));
        System.out.println(result);

        //等价于
        Integer result2 = students.stream().collect(Collectors.reducing(0, Student::getAge, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }

    private static void reducingBinaryOperator(List<Student> students) {
        //通过reducing获取最大年龄
        students.stream()
                .collect(
                        Collectors.reducing(
                                BinaryOperator.maxBy(
                                        Comparator.comparingInt(Student::getAge))))
                .ifPresent(System.out::println);
    }

    private static void partitioningByWithPredicate(List<Student> students) {
        //按照性别分区，男的一类，其余的分为另一类
        Map<Boolean, List<Student>> partitionMap = students.stream().collect(Collectors.partitioningBy(s -> s.getSex().equals("男")));
        System.out.println(partitionMap);

        //对分区后的年龄求平均值
        Map<Boolean, Double> partitionAvgMap = students.stream().collect(Collectors.partitioningBy(s -> s.getSex().equals("男"), Collectors.averagingDouble(Student::getAge)));
        System.out.println(partitionAvgMap);
    }

    private static void joiningWithDelimiterAndPrefixAndSuffix(List<Student> students) {
        //只做字符串分割
        Optional.ofNullable(students.stream().map(Student::getName).collect(Collectors.joining(","))).ifPresent(System.out::println);

        //字符串分割+前后缀
        Optional.ofNullable(students.stream().map(Student::getName).collect(Collectors.joining(",", "Name[", "]"))).ifPresent(System.out::println);
        //效果同上,map----->mapping
        Optional.ofNullable(students.stream().collect(Collectors.mapping(Student::getName, Collectors.joining(",", "Name[", "]")))).ifPresent(System.out::println);

    }

    private static void maxBy(List<Student> students) {
        students.stream().collect(Collectors.maxBy(Comparator.comparingInt(Student::getAge))).ifPresent(System.out::println);
    }

    private static void minBy(List<Student> students) {
        students.stream().collect(Collectors.minBy(Comparator.comparingInt(Student::getAge))).ifPresent(System.out::println);
    }
}
