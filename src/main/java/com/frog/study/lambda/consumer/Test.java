package com.frog.study.lambda.consumer;

import com.frog.study.Student;
import lombok.Data;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/15
 */
public class Test {
    private static List<Student> buildStudentList() {
        return Arrays.asList(new Student("张三", 18, "男", "清华"),
                             new Student("李斯", 19, "男", "清华"),
                             new Student("貂蝉", 18, "女", "北大"),
                             new Student("西施", 16, "女", "南开"));
    }

    public static void main(String[] args) {
        List<Student> students =  buildStudentList();
        students.stream().collect(Collectors.groupingBy(Student::getSchool, TreeMap::new, CollectorsUtils.calc()));

        IntStream intStream2 = IntStream.of(12, 3, 34, 67, 100, 99);
        /** 这个是重点，获得当前int数组的统计信息，包括 **/
        IntSummaryStatistics statistics = intStream2.summaryStatistics();
        Stream intStream3 = students.stream();

    }
}
