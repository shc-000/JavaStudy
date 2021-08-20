package com.shc.test;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frog.study.Student;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/1/4
 */
public class Test {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        String da = "2021/9/27半周\n" +
                "9/27-9/30";
        System.out.println(da.replace("\n"," "));
    }
    @org.junit.Test
    public void assertTest(){
        int a = 1;
        Assert.state(a == 2, () -> {
            return "不是你想要的答案啊";
        });
        //a==2不满足则抛出异常
    }
    @org.junit.Test
    public void nowDay1() throws JsonProcessingException {
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate startDate = LocalDate.parse("2018-06-01", df);
//        LocalDate endDate = startDate.plusDays(6);
////        String localTime = df.format(time);
//        System.out.printf(df.format(startDate));
//        System.out.printf(df.format(endDate));

        /*LocalDate today = LocalDate.now();
        //本周一
        LocalDateTime monday = getLocalDateByParameters(today, DayOfWeek.MONDAY, 0).atStartOfDay();
        //第二天
        LocalDateTime nextDay = today.plusDays(1).atStartOfDay();
        System.out.println(monday);
        System.out.println(nextDay);*/
//        System.out.println(12%3);
//        System.out.println(9%2);
        System.out.println(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }


    public static LocalDate getLocalDateByParameters(LocalDate localDate,DayOfWeek dayOfWeek,Integer index){
        LocalDate afterChange = localDate.plusDays(7 * (index + 1));
        int targetValue = dayOfWeek.getValue();
        int cursorValue = afterChange.getDayOfWeek().getValue();
        if (cursorValue > targetValue){
            return afterChange.with(TemporalAdjusters.previous(dayOfWeek)).with(TemporalAdjusters.previous(dayOfWeek));
        }else{
            return afterChange.with(TemporalAdjusters.previous(dayOfWeek));
        }
    }

    @org.junit.Test
    public void validNum() {
        System.out.println(isNumeric("0"));
    }
    /**
     * 匹配是否为数字
     * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
     */
    private boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("[0-9]+");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        return isNum.matches();
    }

    @org.junit.Test
    public void testList() {
//        String a = "a";
//        String x = "b";
//        String b = "b";
//        if (Objects.equals(a, b)) {
//            System.out.println(a);
//        }

        IntStream intStream=IntStream.rangeClosed(1,16);
        intStream.forEach(System.out::println);
        int k=12;
//        String s = String.format("%05d", k);
//        System.out.println(s);

//        int[] arr = new int[3];
//        Integer[] integers1 = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        Integer[] intArr = new Integer[3];
//        Arrays.stream(arr).boxed().
        JSONArray jsonArray=new JSONArray(Arrays.asList(intArr));
        System.out.println(jsonArray.toString());
//
//        String str1 = Arrays.stream(arr).boxed().map(i -> i.toString()) //必须将普通数组 boxed才能 在 map 里面 toString
//                .collect(Collectors.joining(""));
//        System.out.println(str1);
//
//        String str2 = Arrays.stream(arr).boxed().map(i -> i.toString()).reduce("", String::concat);
//        System.out.println(str2);
//
//        String str3 = Arrays.stream(arr).boxed().map(Object::toString).reduce("", String::concat); // 方法引用Object：：toString
//        System.out.println(str3);

    }

    @org.junit.Test
    public void getFirstDayInWeek() {
        Calendar calendar = Calendar.getInstance();
        //获取当年第几周
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        //获取当周星期几
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //如果是周日，说明是上周的而不是本周的，要获取到上周，后退一天
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            calendar.add(Calendar.DATE, -1);
            weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            //如果是周日，直接设置星期为7
            dayOfWeek = 7;
        } else {
            //如果不是周日，星期减一
            dayOfWeek--;
        }
        //最终获取周+星期
        System.out.println(String.format("今天是第%d周，星期%d", weekOfYear, dayOfWeek));
    }

    @org.junit.Test
    public void getWeekByDate() {
        Calendar calendar = Calendar.getInstance();
        //获取当周星期几
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //如果是周日，说明是上周的而不是本周的，要获取到上周，后退一天
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            //如果是周日，直接设置星期为7
            dayOfWeek = 7;
        } else {
            //如果不是周日，星期减一
            dayOfWeek--;
        }
        System.out.println(dayOfWeek);
    }

    @org.junit.Test
    public void nowDay() {
        LocalDate from = LocalDate.now();
        LocalDate monday = from.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);

        LocalDate sunday = from.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
        System.out.println(monday.atTime(7, 0, 0).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        System.out.println(monday + "_" + sunday);
    }

    @org.junit.Test
    public void nowMap() {
        /*Map<String, Integer> map =Maps.newHashMap();
//        map.put("b",1);
        int a = Optional.ofNullable(map.get("a")).orElse(0);
        System.out.println(a);
        int timeWindowLen = 3;
        int leadTime = 2;
        for (int i = 0, len = (timeWindowLen - (i + leadTime - 1)); i < len; i++) {
            System.out.println(i);
        }*/
        List<Student> students = Lists.newArrayList();
        students.add(Student.builder().name("1s").school("ab").age(1).build());
        students.add(Student.builder().name("2s").school("cd").age(2).build());
        students.add(Student.builder().name("3s").school("ef").age(3).build());
        Map<String, Student> studentMap = students.stream().collect(Collectors.toMap(Student::getName, Function.identity()));
        Student student = studentMap.get("1s");
        student.setAge(6);
        studentMap.putIfAbsent("4s",Student.builder().name("4s").school("hi").age(4).build());
        studentMap.putIfAbsent("2s",Student.builder().name("2s").school("cd").age(2).build());
//        studentMap.putIfAbsent()
//        Student.builder().name("1").age(1).build();
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        if (Calendar.THURSDAY == calendar.get(Calendar.DAY_OF_WEEK)){
            System.out.println(123);
        }

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        long whichDay1 = cal.get(Calendar.DAY_OF_WEEK);
//        System.out.println(whichDay1);
//        System.out.println(fetchDayOfWeek(new Date()));
    }

    protected int fetchDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //如果是周日，说明是上周的而不是本周的，要获取到上周，后退一天
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            //如果是周日，直接设置星期为7
            dayOfWeek = 7;
        } else {
            //如果不是周日，星期减一
            dayOfWeek--;
        }
        return dayOfWeek;
    }

    @org.junit.Test
    public void test() {
//        BinaryOperator<Long> binaryOperator = (x, y) -> x + y;
//
//        System.out.println(binaryOperator.apply(1L,2L).toString());
//        String ss = "2021-01-14 00:00:00";
        LocalDateTime supplyDt = LocalDateTime.parse("2021-01-14 00:00:00", DF);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(formatter.format(supplyDt));

        supplyDt = LocalDateTime.now();

        String supplyDtStr = Objects.isNull(supplyDt) ? "null" : DF.format(supplyDt);

        System.out.println(supplyDtStr);

    }

    @org.junit.Test
    public void split() {
        if (Objects.isNull(StringUtils.split("asd", ","))) {
            System.out.println(123);
        }
        System.out.println(StringUtils.split("asd", ",")[0]);

    }

    @org.junit.Test
    public void testCalc() {
        double dividend = 5;
        double divisor = 7;
        Double flag = Math.ceil(dividend / divisor);    // 向上取整计算int = Math.ceil(int)，对int整数取整，纯属多余！
        System.out.println(flag.intValue());
    }

    @org.junit.Test
    public void testForeach() {
        List<String> list = Stream.of("a", "d", "c", "b").collect(Collectors.toList());

        List listA = new ArrayList();
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;//使用方法引用的方式创建函数式接口引用的实例，等价于使用匿名内部类或Lambda表达式
        List<Integer> intList = Stream.generate(random).limit(10).collect(Collectors.toList());
        intList.forEach(r -> {
            List listb = new ArrayList();
            System.out.println("--->" + r);
            for (String str : list) {
                System.out.println("--->" + str);
                if ("b".equals(str)) {
                    return;
                }

                listb.add(str + r);
            }
            listA.addAll(listb);
        });

        System.out.println(listA);
    }

    @org.junit.Test
    public void testListSet() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        System.out.println(list);
        list.set(1, 6);//替换指定位置的数，原来的数据被覆盖
        list.add(2, 8);//替换指定位置的数，原来的数据后移一位
        System.out.println(list);
//        int[] dependentDemandPlanArr = new int[10];
//        for (int i = 0; i <dependentDemandPlanArr.length; i++){
//            System.out.println(dependentDemandPlanArr[i]);
//        }
//        int[] arr = new int[5];
//        List<Integer> list2 = Arrays.stream(Arrays.copyOfRange(arr,1,arr.length)).boxed().collect(Collectors.toList());
//        System.out.println(list2);
//        int[] arr1 = list2.stream().mapToInt(Integer::valueOf).toArray();
//        System.out.println(arr1[2]);
//        int[] arr = {1,2,3};
//        int[] arr2 = {1,2,3};
//        IntUnaryOperator operator = i -> arr[i] + arr2[i];
//        int[] result = IntStream.range(0,arr.length).map(operator).toArray();
//
//        System.out.println(this.getClass().getName());
    }

    public <T> T jsonToMap(String jsonValue) throws JsonProcessingException {
        if (StringUtils.isEmpty(jsonValue)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonValue, new TypeReference<T>() {
        });
    }

    @org.junit.Test
    public void testJSONType() {
        try {
            String jsonValue = "{\"3\":[100,101,102]}";
            //Map<Index,List<Integer>> clazzMap = new LinkedHashMap<>();
            Map<Integer, List<Integer>> aa = jsonToMap(jsonValue);
            System.out.println(aa);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void testDate() {
        //3个月前的1号
        LocalDate date = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).minusMonths(3);

        LocalDate date1 = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        //当周周一
        LocalDate date2 = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    @org.junit.Test
    public void testInit() {
        int[] arr = new int[3];
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        System.out.println(list);
    }
}
