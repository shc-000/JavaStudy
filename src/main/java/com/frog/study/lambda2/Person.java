package com.frog.study.lambda2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/1/24
 */
@Setter
@Getter
@AllArgsConstructor
public class Person {
    private Integer age;
    private String gender;
    private String emailAddress;
    private String name;
    private Integer birthday;

    public void printPerson() {
        System.out.println("打印person");
    }

    public static int compareByAge(Person a, Person b) {
        return 1;
    }

    public static List<Person> createRoster() {

        return Arrays.asList(new Person(18, "女", "127@qq.com", "西施", 18),
                new Person(16, "女", "128@163.com", "貂蝉", 16),
                new Person(19, "MALE", "128@163.com", "子龙", 19),
                new Person(21, "MALE", "167@qq.com", "吕布", 21)
        );
    }
}
