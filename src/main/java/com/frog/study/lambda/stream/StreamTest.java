package com.frog.study.lambda.stream;

import com.frog.study.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/16 10:53 下午
 */
public class StreamTest {
    public static void main(String[] args) {
//        skipVsLimit();
//        flatMap();
//        distinct();
//        reUseStream();
//        peek();
        filterTest();
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
     * 多条件的filter
     */
    private static void filterTest() {
        List<Student> students = buildStudentList();
//        Predicate是函数式接口，可以直接用lambda代替；如果有复杂的过滤逻辑，则用or、and、negate方法组合
        Predicate<Student> f1 = student -> student.getName().equals("张三");
        Predicate<Student> f2 = student -> student.getName().equals("李斯");
        students.stream().filter(f1.or(f2)).forEach(System.out::println);
    }

    private static void skipVsLimit() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        list.stream().skip(5).forEach(System.out::println);
        list.stream().limit(5).forEach(System.out::println);
    }

    /**
     * flatMap扁平化
     */
    private static void flatMap() {
        //扁平化
        String[] words = {"hello", "world"};
        //过滤掉重复的字母
        Stream<String[]> stream = Arrays.stream(words).map(w -> w.split(""));
        stream.flatMap(Arrays::stream).distinct().forEach(System.out::println);
    }

    /**
     * 借助distinctByKey方法，实现基于多个组合属性的去重复操作
     */
    private static void distinct() {
        List<Student> students = buildStudentList();
        students.stream().filter(distinctByKey(s -> s.getAge() + "_" + s.getSchool())).forEach(System.out::println);
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
     * 复用创建好的stream
     */
    private static void reUseStream() {
        List<Student> students = buildStudentList();
        //后面可接别的操作
        Supplier<Stream<Student>> supplierStudentStream = students::stream;
        supplierStudentStream.get().forEach(System.out::println);
        System.out.println("----------------");
        supplierStudentStream.get().forEach(System.out::println);
    }

    /**
     * peek vs map
     * <p>
     * peek方法接收一个Consumer的入参。了解λ表达式的应该明白 Consumer的实现类 应该只有一个方法，该方法返回类型为void。
     * Stream<T> peek(Consumer<? super T> action);
     * <p>
     * map方法的入参为 Function，有返回值
     * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
     * <p>
     * peek接收一个没有返回值的λ表达式，可以做一些输出，外部处理等。
     * map接收一个有返回值的λ表达式，之后Stream的泛型类型将转换为map参数λ表达式返回的类型
     */
    private static void peek() {
        List<Student> students = buildStudentList();

        students.stream().peek(System.out::println).map(Student::getName).peek(s -> System.out.println("________")).forEach(System.out::println);
    }

    /**
     * 根据给定的function进行聚合操作，可以通过reduce实现很多操作
     */
    private static void reduce() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
        //reduce有三个构造函数
        //第一个，给定初始值的,直接返回值
        Integer sum = Arrays.stream(arr).reduce(0, (i, j) -> i + j);

        //第二个,没有初始值的返回的是Optional
        Integer sum2 = Arrays.stream(arr).reduce((i, j) -> i + j).get();

        //第三个(该构造器第三个参数是在并行计算时用到的，用于多个任务的结果集的合并)，见下面，使用reduce实现map
    }

    /**
     * 使用reduce和lambda表达式来实现map
     */
    public static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
        return stream.reduce(new ArrayList<O>(), (acc, x) -> {
            List<O> newAcc = new ArrayList<>(acc);
            newAcc.add(mapper.apply(x));
            return newAcc;
        }, (List<O> left, List<O> right) -> {
            // We are copying left to new list to avoid mutating it.
            List<O> newLeft = new ArrayList<>(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }
}
