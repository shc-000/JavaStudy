package com.frog.study.lambda.stream;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 定义stream中使用的函数式，对现有stream方法作出扩展
 * 有些不必转为stream就可执行函数式操作，自定义的
 *
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2020/12/22
 */
public class MyStreamTest {


    /**
     * 按照对象的属性或者属性的组合信息去重复，目前stream的distinct不支持，现在基于filter作出扩展方法
     */
    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        //apply将函数应用到参数身上，并返回值
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    //sorted多重条件排序
    //skip条件跳过
    //reduce原则上可以实现stream中很多的方法
    //collect

}
