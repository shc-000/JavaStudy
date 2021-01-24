package com.shc.test;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BinaryOperator;
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
    public void test(){
//        BinaryOperator<Long> binaryOperator = (x, y) -> x + y;
//
//        System.out.println(binaryOperator.apply(1L,2L).toString());
//        String ss = "2021-01-14 00:00:00";
        LocalDateTime supplyDt  = LocalDateTime.parse("2021-01-14 00:00:00", DF);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(formatter.format(supplyDt));

        supplyDt = LocalDateTime.now();

        String supplyDtStr =  Objects.isNull(supplyDt)?"null":DF.format(supplyDt);

        System.out.println(supplyDtStr);

    }

    @org.junit.Test
    public void split(){
        if (Objects.isNull(StringUtils.split("asd", ","))){
            System.out.println(123);
        }
        System.out.println(StringUtils.split("asd", ",")[0]);

    }

    @org.junit.Test
    public void testCalc(){
        double dividend = 5;
        double divisor = 7;
       Double flag = Math.ceil(dividend / divisor);	// 向上取整计算int = Math.ceil(int)，对int整数取整，纯属多余！
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
            System.out.println("--->"+r);
            for (String str : list) {
                System.out.println("--->"+str);
                if ("b".equals(str)) {
                    return;
                }

                listb.add(str + r);
            }
            listA.addAll(listb);
        });

        System.out.println(listA);
    }
}
