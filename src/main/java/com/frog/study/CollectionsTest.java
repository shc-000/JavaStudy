package com.frog.study;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/15 9:22 下午
 */
public class CollectionsTest {
    private List<Student> buildStudentList() {
        return Arrays.asList(new Student("张三", 18, "男", "清华"),
                new Student("李斯", 19, "男", "清华"),
                new Student("貂蝉", 18, "女", "北大"),
                new Student("西施", 16, "女", "南开"));
    }

    public static void main(String[] args) {
        CollectionsTest collectionsTest = new CollectionsTest();
        List<Student> students = collectionsTest.buildStudentList();
//        collectionsTest.sort(students);
        collectionsTest.merge();
    }

    private void sort(List<Student> students) {
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
        students = students.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        students.forEach(student -> System.out.println(student.toString()));
    }

    /**
     * 对已经存在的key对应的value的值做操作
     */
    private void merge() {
        Map<String, String> map = new HashMap<>();
        map.put("e", "E");
        map.merge("f", "F", String::concat);
        System.out.println("map.get(\"f\")=" + map.get("f"));
        System.out.println("map.get(\"e\")=" + map.get("e"));
        map.merge("e", "F", String::concat);
        map.merge("e", "F", String::concat);
        map.merge("e", "K", String::concat);
        System.out.println("map.get(\"f\")=" + map.get("f")); // map.get("f")=F
        System.out.println("map.get(\"e\")=" + map.get("e")); // map.get("e")=EF
    }

    /**
     * 多属性去重复(弃用)，可以使用StreamTest中的distinctByKey实现
     */
    @Deprecated
    private static List<Student> removeDuplicateOrder(List<Student> orderList) {
        Set<Student> set = new TreeSet<Student>(new Comparator<Student>() {
            @Override
            public int compare(Student a, Student b) {
                if (a.getSex().equals(b.getSex()) && a.getName().equals(b.getName())) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        set.addAll(orderList);
        return new ArrayList<Student>(set);
    }
}
