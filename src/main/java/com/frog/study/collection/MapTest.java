package com.frog.study.collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/3/30
 */
public class MapTest {

    @Test
    public void containsKey() {
        Map<String,List<String>> map = Maps.newHashMap();
        List<String> aList = map.get("a");
        List<String> bList = aList.stream().collect(Collectors.toList());
    }

    @Test
    public void testGuavaMap(){

    }

    @Test
    public void containsValue() {
        String key1 = "a";
        String key2 = "k";
        Map<String, Map<String, Double>> itemSiteBucketValueMap = Maps.newHashMap();
        itemSiteBucketValueMap.put(key1, new HashMap<String, Double>() {{
            put(key2, 2d);
        }});
        System.out.println(itemSiteBucketValueMap.get("a"));
    }

    @Test
    public void mutiMap() {
        Multimap<String,String> multimap = ArrayListMultimap.create();

        multimap.put("lower", "a");
        multimap.put("lower", "b");
        multimap.put("lower", "c");

        multimap.put("upper", "A");
        multimap.put("upper", "B");

        System.out.println(multimap.asMap());
    }

    //compute() 方法对 hashMap 中指定 key 的值进行重新计算。指定的key在map中的值进行操作 不管存不存在，操作完成后保存到map中
    //如果 key 对应的 value 不存在，则返回该 null，如果存在，则返回通过 remappingFunction 重新计算后的值。
    @Test
    public void compute() {
        //统计一个字符串中各个单词出现的频率，然后从中找出频率最高的单词。
        String str = "hello java, i am vary happy! nice to meet you";

        // jdk1.8的写法
        HashMap<Character, Integer> result2 = new HashMap<>(32);
        for (int i = 0; i < str.length(); i++) {
            char curChar = str.charAt(i);
            result2.compute(curChar, (k, v) -> {
                if (v == null) {
                    v = 1;
                } else {
                    v += 1;
                }
                return v;
            });
        }
    }

    /*
    //java8之前。从map中根据key获取value操作可能会有下面的操作
    Object key = map.get("key");
    if (key == null) {
        key = new Object();
        map.put("key", key);
    }

    // java8之后。上面的操作可以简化为一行，若key对应的value为空，会将第二个参数的返回值存入并返回
    Object key2 = map.computeIfAbsent("key", k -> new Object());*/

    //computeIfAbsent的方法有两个参数 第一个是所选map的key，第二个是需要做的操作。这个方法当key值不存在时才起作用。
    //当key存在返回当前value值，不存在执行函数并保存到map中
    @Test
    public void computeIfAbsent() {
        Map<String, String> mmp = new HashMap<>();
        mmp.putIfAbsent("a", "v");
        mmp.putIfAbsent("a", "k");
        System.out.println(mmp);
        //putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值。
        Map<String, List<Integer>> map = new HashMap<>();
        System.out.println(map);
        //computeIfAbsent在放入数据时，若key对应的value为空，会将第二个参数的返回值存入并返回
        map.computeIfAbsent("a", l -> new ArrayList<>()).add(6);
        System.out.println(map);
        map.computeIfAbsent("a", l -> new ArrayList<>()).add(7);
        Map<String,Integer> mm = Maps.newHashMap();
        mm.computeIfAbsent("b", v -> 1);
        System.out.println(mm);

    }


    //computeIfPresent 的方法,对 指定的 在map中已经存在的key的value进行操作。只对已经存在key的进行操作，其他不操作
    @Test
    public void computeIfPresent() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        //只对map中存在的key对应的value进行操作
        Integer integer = map.computeIfPresent("3", (k, v) -> v + 1);
        Integer integer1 = map.computeIfPresent("4", (k, v) -> {
            if (v == null) {
                return 0;
            }
            return v + 1;
        });
        System.out.println(integer);
        System.out.println(integer1);
        System.out.println(map.toString());
    }


    /*merge() 可以这么理解：它将新的值赋值到 key （如果不存在）或更新给定的key 值对应的 value，
    该方法接收三个参数，一个 key 值，一个 value，一个 remappingFunction ，
    如果给定的key不存在，它就变成了 put(key, value) 。但是，如果 key 已经存在一些值，
    我们  remappingFunction 可以选择合并的方式，然后将合并得到的 newValue 赋值给原先的 key。

    这个使用场景相对来说还是比较多的，比如分组求和这类的操作，虽然 stream 中有相关 groupingBy() 方法，
    但如果你想在循环中做一些其他操作的时候，merge() 还是一个挺不错的选择的。
    */
    @Test
    public void merge() throws JsonProcessingException {
        //假设我们有这么一段业务逻辑，我有一个学生成绩对象的列表，对象包含学生姓名、科目、科目分数三个属性，要求求得每个学生的总成绩。可以用merge
        Map<String, Integer> studentScoreMap2 = new HashMap<>();
        List<StudentScore> studentScoreList = buildTestList();
        studentScoreList.forEach(studentScore -> studentScoreMap2.merge(
                studentScore.getStuName(),
                studentScore.getScore(),
                Integer::sum));
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(studentScoreMap2));
    }

    private List<StudentScore> buildTestList() {
        List<StudentScore> studentScoreList = new ArrayList<>();
        StudentScore studentScore1 = new StudentScore() {{
            setStuName("张三");
            setSubject("语文");
            setScore(70);
        }};
        StudentScore studentScore2 = new StudentScore() {{
            setStuName("张三");
            setSubject("语文");
            setScore(71);
        }};
        studentScoreList.add(studentScore1);
        studentScoreList.add(studentScore2);
        return studentScoreList;
    }


    // 静态Map的初始化，实际情况中，可以使用枚举代替
    // 方法一
    public static final Map<Integer, String> myMap_0 = new HashMap<Integer, String>();

    static {
        myMap_0.put(1, "one");
        myMap_0.put(2, "two");
    }

    // 方法二 JDK5
    public static final Map<Integer, String> myMap_1 = new HashMap<Integer, String>() {{
        put(1, "one");
        put(2, "two");
    }};
    // 方法三  Guava
    static final Map<Integer, String> myMap_2 = ImmutableMap.<Integer, String>builder().put(1, "one").put(2, "two").build();

    // 方法四，不超过5个的时候  Guava
    static final Map<Integer, String> myMap_3 = ImmutableMap.of(
            1, "one",
            2, "two"
    );

    // 方法五  Collections.unmodifiableMap()  通过工具类实现


    // 方法六 JDK8
    static final Map<Integer, String> MY_MAP = Arrays.stream(new Object[][]{
            {1, "one"},
            {2, "two"},
    }).collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));


    private static final Map<Integer, String> myMap = Stream.of(
            new AbstractMap.SimpleEntry<>(1, "one"),
            new AbstractMap.SimpleEntry<>(2, "two"),
            new AbstractMap.SimpleEntry<>(3, "three"),
            new AbstractMap.SimpleEntry<>(4, "four"),
            new AbstractMap.SimpleEntry<>(5, "five"),
            new AbstractMap.SimpleEntry<>(6, "six"),
            new AbstractMap.SimpleEntry<>(7, "seven"),
            new AbstractMap.SimpleEntry<>(8, "eight"),
            new AbstractMap.SimpleEntry<>(9, "nine"),
            new AbstractMap.SimpleEntry<>(10, "ten"))
            .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    @Data
    public static class StudentScore {
        private String stuName;
        private Integer score;
        private String subject;
    }
}
