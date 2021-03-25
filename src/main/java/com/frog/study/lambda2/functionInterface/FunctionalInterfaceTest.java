package com.frog.study.lambda2.functionInterface;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.*;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/1/24
 */
public class FunctionalInterfaceTest {
    //1. 断⾔言型接⼝口:Predicate<T> 将满⾜足条件的字符串串放⼊入集合
    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> newList = new ArrayList<>();
        for (String s : list) {
            if (predicate.test(s)) {
                newList.add(s);
            }
        }
        return newList;
    }

    //2.函数型接⼝口:Function<T, R> 处理理字符串串
    public String strHandler(String str, Function<String, String> function) {
        return function.apply(str);
    }

    //3.供给型接⼝口 :Supplier<T> 产⽣生指定个数的整数，并放⼊入集合
    public List<Integer> getNumList(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = supplier.get();
            list.add(n);
        }
        return list;
    }

    //4.消费型接⼝口 :Consumer<T> 修改参数
    public void modifyValue(Integer value, Consumer<Integer> consumer) {
        consumer.accept(value);
    }

    /**
     * 带索引遍历
     *
     * @param elements 集合
     * @param action   消费
     * @param <T>      泛型
     */
    private static <T> void forEach(Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        //从0开始遍历的索引
        int startIndex = 0;
        int index = 0;
        for (T element : elements) {
            index++;
            if (index <= startIndex) {
                continue;
            }
            action.accept(index - 1, element);
        }
    }

    public static void main(String[] args) {
        //5.测试带索引遍历
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> lista = Arrays.asList(1, 2, 3);
        List<Integer> listb = Arrays.asList(4, 5, 6);
        List<Integer> listc = Arrays.asList(7, 8, 9);
        lists.add(lista);
        lists.add(listb);
        lists.add(listc);
        List<Integer> result = new ArrayList<>();
        lists.forEach(l -> forEach(l, (index, item) -> {
            if (result.size() < l.size()) {
                result.add(index, item + fetchById(result, index));
            } else {
                result.set(index, item + fetchById(result, index));
            }
        }));
        System.out.println(result);
    }

    public static void main1(String[] args) {
        List<String> list = Arrays.asList("hello", "java8", "function", "predicate");
        FunctionalInterfaceTest test = new FunctionalInterfaceTest();
        //1.测试断言性接口
        List<String> newList = test.filterStr(list, s -> s.length() > 5);
        for (String s : newList) {
            System.out.println(s);
        }

        //2.测试函数性接口
        String str1 = test.strHandler("测试内置函数式接⼝口", s -> s.substring(2));
        System.out.println(str1);
        String str2 = test.strHandler("abcdefg", s -> s.toUpperCase());
        System.out.println(str2);

        //3.测试供给型接口
        List<Integer> numList = test.getNumList(10, () -> (int) (Math.random() * 100));
        numList.forEach(System.out::println);

        //4.测试消费型接口
        test.modifyValue(3, s -> System.out.println(s * 3));

        //5.测试带索引遍历
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> lista = Arrays.asList(1, 2, 3);
        List<Integer> listb = Arrays.asList(4, 5, 6);
        List<Integer> listc = Arrays.asList(7, 8, 9);
        lists.add(lista);
        lists.add(listb);
        lists.add(listc);
        List<Integer> result = new ArrayList<>();
        lists.forEach(l -> forEach(l, (index, item) -> result.set(index, item + fetchById(result, index))));
        System.out.println(result);
    }

    static int fetchById(List<Integer> list, int i) {
        if (CollectionUtils.isEmpty(list) || list.size() - 1 < i) {
            return 0;
        }
        return list.get(i);
    }
}
