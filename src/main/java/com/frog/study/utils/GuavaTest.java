package com.frog.study.utils;

import com.frog.study.lambda2.Person;
import com.google.common.base.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/06/13
 */
public class GuavaTest {

    //集合创建
    @Test
    public void collectionCreate() {
        // 普通Collection的创建
        List<String> list = Lists.newArrayList();
        Set<String> set = newHashSet();
        Map<String, String> map = Maps.newHashMap();

        // 不变Collection的创建
        /*
         * 1.在多线程操作下，是线程安全的
         * 2.所有不可变集合会比可变集合更有效的利用资源
         * 3.中途不可改变
         * */
        ImmutableList<String> iList = ImmutableList.of("a", "b", "c");
        ImmutableSet<String> iSet = ImmutableSet.of("e1", "e2");
        ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2");

        //当我们需要一个map中包含key为String类型，value为List类型的时候，以前我们是这样写的
        Map<String, List<Integer>> mapl = new HashMap<String, List<Integer>>();
        List<Integer> listl = new ArrayList<Integer>();
        listl.add(1);
        listl.add(2);
        mapl.put("aa", listl);
        System.out.println(mapl.get("aa"));//[1, 2]

        //而现在
        Multimap<String, Integer> mapm = ArrayListMultimap.create();
        mapm.put("aa", 1);
        mapm.put("aa", 2);
        System.out.println(mapm.get("aa"));  //[1, 2]

//        其他的黑科技集合
        /*MultiSet: 无序+可重复   count()方法获取单词的次数  增强了可读性+操作简单
        创建方式:  Multiset<String> set = HashMultiset.create();

        Multimap: key-value  key可以重复
        创建方式: Multimap<String, String> teachers = ArrayListMultimap.create();

        BiMap: 双向Map(Bidirectional Map) 键与值都不能重复
        创建方式:  BiMap<String, String> biMap = HashBiMap.create();

        Table: 双键的Map Map--> Table-->rowKey+columnKey+value  //和sql中的联合主键有点像
        创建方式: Table<String, String, Integer> tables = HashBasedTable.create();

        ...等等(guava中还有很多java里面没有给出的集合类型)*/
    }

    //将集合转换为特定规则的字符串
    @Test
    public void collectionTrans() {
        //以前我们将list转换为特定规则的字符串是这样写的:
        //use java
        List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            str = str + "-" + list.get(i);
        }
        //str 为-aa-bb-cc

        //use guava 我们这样写
        List<String> lista = new ArrayList<String>();
        lista.add("aa");
        lista.add("bb");
        lista.add("cc");
        String result = Joiner.on("-").join(lista);
        //result为  aa-bb-cc

        //把map集合转换为特定规则的字符串
        Map<String, Integer> mapp = Maps.newHashMap();
        mapp.put("xiaoming", 12);
        mapp.put("xiaohong", 13);
        String resultp = Joiner.on(",").withKeyValueSeparator("=").join(mapp);
        // result为 xiaoming=12,xiaohong=13
    }

    //将String转换为特定的集合
    @Test
    public void stringToCollection() {
        //use java
        List<String> list = new ArrayList<String>();
        String a = "1-2-3-4-5-6";
        String[] strs = a.split("-");
        for (int i = 0; i < strs.length; i++) {
            list.add(strs[i]);
        }

        //use guava
        String str = "1-2-3-4-5-6";
        List<String> lista = Splitter.on("-").splitToList(str);
        //lista为  [1, 2, 3, 4, 5, 6]
        //        如果str="1-2-3-4- 5-  6  ";有空格
        //guava还可以使用 omitEmptyStrings().trimResults() 去除空串与空格
        String stra = "1-2-3-4-  5-  6   ";
        List<String> listb = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(stra);
        System.out.println(listb);

        //将String转换为map
        String strb = "xiaoming=11,xiaohong=23";
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(strb);
    }

    //guava还支持多个字符切割，或者特定的正则分隔
    @Test
    public void charSplit() {
        String input = "aa.dd,,ff,,.";
        List<String> resultb = Splitter.onPattern("[.|,]").omitEmptyStrings().splitToList(input);
        //关于字符串的操作 都是在Splitter这个类上进行的
        // 判断匹配结果
        boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K'); //true

        // 保留数字文本  CharMatcher.digit() 已过时   retain 保留
        //String s1 = CharMatcher.digit().retainFrom("abc 123 efg"); //123
        String s1 = CharMatcher.inRange('0', '9').retainFrom("abc 123 efg"); // 123

        // 删除数字文本  remove 删除
        // String s2 = CharMatcher.digit().removeFrom("abc 123 efg");    //abc  efg
        String s2 = CharMatcher.inRange('0', '9').removeFrom("abc 123 efg"); // abc  efg
    }

    //集合的过滤
    @Test
    public void collectionFilter() {
        //我们对于集合的过滤，思路就是迭代，然后再具体对每一个数判断，这样的代码放在程序中，难免会显得很臃肿，虽然功能都有，但是很不好看。
        //guava写法
        //按照条件过滤
        ImmutableList<String> names = ImmutableList.of("begin", "code", "Guava", "Java");
        Iterable<String> fitered = Iterables.filter(names, Predicates.or(Predicates.equalTo("Guava"), Predicates.equalTo("Java")));
        System.out.println(fitered); // [Guava, Java]

        //自定义过滤条件   使用自定义回调方法对Map的每个Value进行操作
        ImmutableMap<String, Integer> m = ImmutableMap.of("begin", 12, "code", 15);
        // Function<F, T> F表示apply()方法input的类型，T表示apply()方法返回类型
        Map<String, Integer> m2 = Maps.transformValues(m, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                if (input > 12) {
                    return input;
                } else {
                    return input + 1;
                }
            }
        });
        System.out.println(m2);   //{begin=13, code=15}


        //set的交集, 并集, 差集
        HashSet setA = newHashSet(1, 2, 3, 4, 5);
        HashSet setB = newHashSet(4, 5, 6, 7, 8);

        Sets.SetView<Integer> union = Sets.union(setA, setB);
        System.out.println("union:");
        for (Integer integer : union) {
            System.out.println(integer);           //union 并集:12345867
        }

        Sets.SetView<Integer> difference = Sets.difference(setA, setB);
        System.out.println("difference:");
        for (Integer integer : difference) {
            System.out.println(integer);        //difference 差集:123
        }

        Sets.SetView<Integer> intersection = Sets.intersection(setA, setB);
        System.out.println("intersection:");
        for (Integer integer : intersection) {
            System.out.println(integer);  //intersection 交集:45
        }


//        map的交集，并集，差集
        HashMap<String, Integer> mapA = Maps.newHashMap();
        mapA.put("a", 1);mapA.put("b", 2);mapA.put("c", 3);

        HashMap<String, Integer> mapB = Maps.newHashMap();
        mapB.put("b", 20);mapB.put("c", 3);mapB.put("d", 4);

        MapDifference differenceMap = Maps.difference(mapA, mapB);
        differenceMap.areEqual();
        Map entriesDiffering = differenceMap.entriesDiffering();
        Map entriesOnlyLeft = differenceMap.entriesOnlyOnLeft();
        Map entriesOnlyRight = differenceMap.entriesOnlyOnRight();
        Map entriesInCommon = differenceMap.entriesInCommon();

        System.out.println(entriesDiffering);   // {b=(2, 20)}
        System.out.println(entriesOnlyLeft);    // {a=1}
        System.out.println(entriesOnlyRight);   // {d=4}
        System.out.println(entriesInCommon);    // {c=3}
    }

    //免去了很多麻烦！并且会使你的代码看上去更好看。而不是代码里面充斥着 !=null， !=""
    @Test
    public void paramValid() {
        int count = -1;
        //use java
        if (count <= 0) {
            throw new IllegalArgumentException("must be positive: " + count);
        }

        //use guava
        Preconditions.checkArgument(count > 0, "must be positive: %s", count);

        //检查是否为空,不仅仅是字符串类型，其他类型的判断，全部都封装在 Preconditions类里，里面的方法全为静态
    }

    @Test
    public void moreObjects(){
// 这个方法是在Objects过期后官方推荐使用的替代品，该类最大的好处就是不用大量的重写 toString，用一种很优雅的方式实现重写，或者在某个场景定制使用。
//        Person person = new Person("aa",11);
//        String str = MoreObjects.toStringHelper("Person").add("age", person.getAge()).toString();
//        System.out.println(str);
//输出Person{age=11}
    }

    //强大的Ordering排序器
//    排序器[Ordering]是Guava流畅风格比较器[Comparator]的实现，它可以用来为构建复杂的比较器，以完成集合排序的功能。

    @Test
    public void orderIng() {
//        natural()   对可排序类型做自然排序，如数字按大小，日期按先后排序
//        usingToString() 按对象的字符串形式做字典排序[lexicographical ordering]
//        from(Comparator)    把给定的Comparator转化为排序器
//        reverse()   获取语义相反的排序器
//        nullsFirst()    使用当前排序器，但额外把null值排到最前面。
//        nullsLast() 使用当前排序器，但额外把null值排到最后面。
//        compound(Comparator)    合成另一个比较器，以处理当前排序器中的相等情况。
//        lexicographical()   基于处理类型T的排序器，返回该类型的可迭代对象Iterable<T>的排序器。
//        onResultOf(Function)    对集合中元素调用Function，再按返回值用当前排序器排序。
        Person person = new Person();
        person.setAge(18);
        person.setName("aa");
        Person ps = new Person();
        ps.setAge(17);
        ps.setName("bb");
        Ordering<Person> byOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<Person,Integer>(){
            @Override
            public Integer apply(Person person){
                return person.age;
            }
        });
        byOrdering.compare(person, ps);
        System.out.println(byOrdering.compare(person, ps)); //1      person的年龄比ps大 所以输出1
    }

    //计算中间代码的运行时间
    @Test
    public void stopWatch(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i=0; i<100000; i++){
            // do some thing
        }
        //TimeUnit 可以指定时间输出精确到多少时间
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);
    }

    //Files类中还有许多方法可以用，可以多多翻阅
    @Test
    public void fileOpt() {
    //以前我们写文件读取的时候要定义缓冲区，各种条件判断，各种 $%#$@#
        //而现在我们只需要使用好guava的api 就能使代码变得简洁，并且不用担心因为写错逻辑而背锅了
        File file = new File("test.txt");
        List<String> list = null;
        try {
            list = Files.readLines(file, Charsets.UTF_8);
        } catch (Exception e) {
        }

//        Files.copy(from,to);  //复制文件
//        Files.deleteDirectoryContents(File directory); //删除文件夹下的内容(包括文件与子文件夹)
//        Files.deleteRecursively(File file); //删除文件或者文件夹
//        Files.move(File from, File to); //移动文件
//        URL url = Resources.getResource("abc.xml"); //获取classpath根下的abc.xml文件url
    }

    //guava的缓存设计的比较巧妙，可以很精巧的使用。guava缓存创建分为两种，一种是CacheLoader,另一种则是callback方式
    @Test
    public void guavaCache() throws ExecutionException {
        //CacheLoader:
        LoadingCache<String,String> cahceBuilder= CacheBuilder
                .newBuilder()
                .build(new CacheLoader<String, String>(){
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue="hello "+key+"!";
                        return strProValue;
                    }
                });
//        System.out.println(cahceBuilder.apply("begincode"));  //hello begincode!
//        System.out.println(cahceBuilder.get("begincode")); //hello begincode!
//        System.out.println(cahceBuilder.get("wen")); //hello wen!
//        System.out.println(cahceBuilder.apply("wen")); //hello wen!
//        System.out.println(cahceBuilder.apply("da"));//hello da!
//        cahceBuilder.put("begin", "code");
        System.out.println(cahceBuilder.get("begin")); //code
        //api中已经把apply声明为过期，声明中推荐使用get方法获取值


        //callback方式:
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        String resultVal = cache.get("code", new Callable<String>() {
            @Override
            public String call() {
                String strProValue="begin "+"code"+"!";
                return strProValue;
            }
        });
        System.out.println("value : " + resultVal); //value : begin code!
    }

    //以上只是guava使用的一小部分，guava是个大的工具类
    @Test
    public void test() {
        List<String> list = new ArrayList<String>();
        list.add("aa");
        Set<String> aa = list.stream().filter("bb"::equals).collect(Collectors.toSet());
        if (aa.contains("aa")) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }
    }
}
