package com.frog.study.lambda.collector;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 根据年龄，统计出婴儿(baby)/少年(young)/成年(grown-up)/老年(elderly)人的分组及名字
 *
 * @author shaohaichao
 * @since 2021/12/20
 */
public class CollectorByAge implements Collector<Person, Map<String, List<String>>, Map<String, List<String>>> {
    @Override
    public Supplier<Map<String, List<String>>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<String, List<String>>, Person> accumulator() {
        return (map, person) -> {
            Integer age = person.getAge();
            if (age < 1) {
                //婴儿
                //存在key则累加
                map.computeIfPresent("baby", (k, v) -> {
                    v.add(person.getName());
                    return v;
                });
                //不存在key新增
                map.putIfAbsent("baby", new ArrayList<String>() {{
                    add(person.getName());
                }});
            } else if (age >= 1 && age < 18) {
                //存在key则累加
                map.computeIfPresent("young", (k, v) -> {
                    v.add(person.getName());
                    return v;
                });
                //不存在key新增
                map.putIfAbsent("young", new ArrayList<String>() {{
                    add(person.getName());
                }});
            } else if (age >= 18 && age < 60) {
                //存在key则累加
                map.computeIfPresent("grown-up", (k, v) -> {
                    v.add(person.getName());
                    return v;
                });
                //不存在key新增
                map.putIfAbsent("grown-up", new ArrayList<String>() {{
                    add(person.getName());
                }});
            } else {
                //存在key则累加
                map.computeIfPresent("elderly", (k, v) -> {
                    v.add(person.getName());
                    return v;
                });
                //不存在key新增
                map.putIfAbsent("elderly", new ArrayList<String>() {{
                    add(person.getName());
                }});
            }
        };
    }

    @Override
    public BinaryOperator<Map<String, List<String>>> combiner() {
        System.out.println("线程信息----->" + Thread.currentThread().getName());
        return (l, r) -> {
            r.forEach((k, v) -> {
                if (l.containsKey(k)) {
                    l.get(k).addAll(v);
                } else {
                    l.putAll(r);
                }
            });
            return l;
        };
    }

    @Override
    public Function<Map<String, List<String>>, Map<String, List<String>>> finisher() {
        return (a) -> a;
    }

    @Override
//    public Set<Characteristics> characteristics() {
//        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
//    }
    public Set<Characteristics> characteristics() {
        // TODO-SHC: 2021/12/20 支持并发
        return Collections.unmodifiableSet(
                EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED));
    }

    public static void main(String[] args) {
        List<Person> personList = getPersonList();
//        Map<String, List<String>> map = personList.stream().collect(new CollectorByAge());
        Map<String, List<String>> map2 = personList.parallelStream().collect(new CollectorByAge());

//        System.out.println(map);
        System.out.println(map2);
    }


    private static List<Person> getPersonList() {
        return Arrays.asList(
                new Person("小花", 0),
                new Person("小明", 14),
                new Person("小马", 19),
                new Person("小坏", 61),
                new Person("小红", 66),
                new Person("小赵", 20)
        );
    }
}
