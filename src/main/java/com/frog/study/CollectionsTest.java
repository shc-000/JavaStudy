package com.frog.study;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public void combine() {
        List<Student> list = buildStudentList();
        Map<String, List<String>> map = list.stream().collect(Collectors.toMap(Student::getName,
        // 此时的value 为集合，方便重复时操作
                s -> {
                    List<String> studentNameList = new ArrayList<>();
                    studentNameList.add(s.getSchool());
                    return studentNameList;
                },
        // 重复时将现在的值全部加入到之前的值内
                (List<String> value1, List<String> value2) -> {
                    value1.addAll(value2);
                    return value1;
                }
        ));
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

    /**
     * 按照对象的属性或者属性的组合信息去重复，目前stream的distinct不支持，现在基于filter作出扩展方法
     */
    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        //apply将函数应用到参数身上，并返回值
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 借助distinctByKey方法，实现基于多个组合属性的去重复操作
     */
    private void distinct() {
        List<Student> students = buildStudentList();
        students.stream().filter(distinctByKey(s -> s.getAge() + "_" + s.getSchool())).forEach(System.out::println);
    }

    @Test
    public void combineStream() {
        CollectionsTest collectionsTest = new CollectionsTest();
        List<Student> students = collectionsTest.buildStudentList();
        List<String> names = students.stream().map(extendMapStr(Student::getName)).collect(Collectors.toList());
        List<Integer> ages = students.stream().map(extendMapInteger(Student::getAge)).collect(Collectors.toList());
        System.out.println(names);
        System.out.println(ages);
    }

    /**
     * Function<T,R></>T表示参数，R表示返回值
     * T1表示该方法是范型方法
     * T2表示入参数类型，R表示返回值
     */
    static <T> Function<? super T, String> extendMapStr(Function<? super T, String> keyExtractor) {
        return keyExtractor.andThen(x -> x + "test");
        //return keyExtractor::apply;
        //上下相等
//        return keyExtractor;
    }

    static <T> Function<? super T, Integer> extendMapInteger(Function<? super T, Integer> keyExtractor) {
        return keyExtractor::apply;
        //上下相等
//        return keyExtractor;
    }
}
