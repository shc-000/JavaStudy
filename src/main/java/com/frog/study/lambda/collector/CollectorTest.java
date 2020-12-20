package com.frog.study.lambda.collector;

import com.frog.study.Student;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collector的重点功能有三种
 * 1：reduce和summarizing(聚合和汇总总结)
 * 2：group分组
 * 3：partition分区
 *
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/19 1:20 下午
 */
public class CollectorTest {
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
     * 按照学校分组的普通实现方式
     */
    private static Map<String, List<Student>> groupByNormal(List<Student> students) {
        Map<String, List<Student>> map = new HashMap<>();
        for (Student student : students) {
            List<Student> list = map.get(student.getSchool());
            if (CollectionUtils.isEmpty(list)) {
                list = new ArrayList<>();
                map.put(student.getSchool(), list);
            }
            list.add(student);
        }
        return map;
    }

    /**
     * 按照学校分组的stream+optional实现
     */
    private static Map<String, List<Student>> groupByFunction(List<Student> students) {
        Map<String, List<Student>> map = new HashMap<>();
        students.forEach(student -> {
            List<Student> schoolList = Optional.ofNullable(map.get(student.getSchool())).orElseGet(() -> {
                List<Student> list = new ArrayList<>();
                map.put(student.getSchool(), list);
                return list;
            });
            schoolList.add(student);
        });
        return map;
    }

    /**
     * 按照学校分组的（collector方式实现）
     */
    private static Map<String, List<Student>> groupByCollector(List<Student> students) {
        return students.stream().collect(Collectors.groupingBy(Student::getSchool));
    }

}
