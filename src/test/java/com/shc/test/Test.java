package com.shc.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/1/4
 */
public class Test {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    public void testDate(){
        //3个月前的1号
        LocalDate date = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).minusMonths(3);

        LocalDate date1 = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        //当周周一
        LocalDate date2 = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    @org.junit.Test
    public void testInit(){
        int[] arr = new int[3];
        for (int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        System.out.println(list);
    }
}
