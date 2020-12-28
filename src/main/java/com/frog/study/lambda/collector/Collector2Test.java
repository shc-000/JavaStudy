package com.frog.study.lambda.collector;

import com.frog.study.Student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/19 2:35 下午
 */
public class Collector2Test {

    public static void main(String[] args) {
        averagingDouble(buildStudentList());
        averagingLong(buildStudentList());
        averagingInt(buildStudentList());

        collectingAndThen(buildStudentList());

        counting(buildStudentList());

        groupByFunction(buildStudentList());
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

    /**
     * 年龄平均数
     * averagingDouble(ToDoubleFunction<? super T> mapper)
     */
    private static void averagingDouble(List<Student> students) {
        Optional.ofNullable(students.stream().collect(Collectors.averagingDouble(Student::getAge)))
                .ifPresent(System.out::println);
    }

    /**
     * averagingLong(ToLongFunction<? super T> mapper)
     */
    private static void averagingLong(List<Student> students) {
        Optional.ofNullable(students.stream().collect(Collectors.averagingLong(Student::getAge)))
                .ifPresent(System.out::println);

    }

    /**
     * averagingInt(ToIntFunction<? super T> mapper)
     */
    private static void averagingInt(List<Student> students) {
        Optional.ofNullable(students.stream().collect(Collectors.averagingInt(Student::getAge)))
                .ifPresent(System.out::println);
    }

    /**
     * 收集到downstream后，接下来怎么去用（finisher）
     * T代表传进去的，A代表累加器，R代表出来的值（downstream）
     * R（downstream）作为入参再传进去，出来RR
     * collectingAndThen(Collector<T,A,R> downstream, Function<R,RR> finisher)
     */
    private static void collectingAndThen(List<Student> students) {
        //对收集到的结果做字符串拼接
        Optional.ofNullable(students.stream().collect(Collectors.collectingAndThen(Collectors.averagingDouble(Student::getAge), s -> "对收集到的平均值拼接字符串" + s)))
                .ifPresent(System.out::println);

        //对搜集到的结果，转化成不可变更的list
        List<Student> umModifyList = students.stream().filter(s -> s.getAge() > 16).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        //下面会报错
        //umModifyList.add(new Student("杨玉环", 18, "女", "中科大"));
        //System.out.println(umModifyList);
    }

    private static void counting(List<Student> students) {
        Optional.ofNullable(students.stream().collect(Collectors.counting())).ifPresent(System.out::println);
//        相当于,只不过上面的在collect中，计算后还可以做别的操作
        Optional.ofNullable(students.stream().count()).ifPresent(System.out::println);
    }

    /**
     * 按照条件分组
     */
    private static void groupByFunction(List<Student> students) {
        //按照类型分组
        Map<String, List<Student>> stuMap = students.stream().collect(Collectors.groupingBy(Student::getSchool));
        System.out.println(stuMap);

        //按照组合属性分组
        Map<String, List<Student>> stuMap1 = students.stream().collect(Collectors.groupingBy(student -> student.getSchool() + "_" + student.getName()));
        System.out.println("按照组合属性分组" + stuMap1);

        //按照不同条件分组
        Map<String, List<Student>> stuMap2 = students.stream().collect(Collectors.groupingBy(student -> {
            if (student.getAge() > 18) {
                return "成年";
            } else {
                return "未成年";
            }
        }));
        System.out.println("按照不同条件分组" + stuMap2);

        //多级分组
        Map<String, Map<String, List<Student>>> stuMap3 = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.groupingBy(student -> {
            if (student.getAge() > 18) {
                return "成年";
            } else {
                return "未成年";
            }
        })));
        System.out.println("多级分组" + stuMap3);

        //统计每种分组类型对应多少个数
        Map<String, Long> mapCount = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.counting()));
        System.out.println(mapCount);

        //统计每种类型的年龄的平均值
        Map<String, Double> mapAver = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.averagingDouble(Student::getAge)));
        System.out.println(mapAver);

        //统计每个年级的年龄和
        Map<String, Integer> mapSum = students.stream().collect(Collectors.groupingBy(Student::getSchool, Collectors.summingInt(Student::getAge)));


        //将返回的结果hashMap转化为treeMap
        Map<String, Double> treeMapAver = students.stream().collect(Collectors.groupingBy(Student::getSchool, TreeMap::new, Collectors.averagingDouble(Student::getAge)));
        System.out.println(treeMapAver);

    }
}
