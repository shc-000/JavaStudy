package com.shc.test;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/1/4
 */
@Setter
@Getter
public class Person {
    private Integer age;
    private String gender;
    private String emailAddress;
    private String name;
    private Integer birthday;
    public void printPerson(){
        System.out.println("打印person");
    }
    public static int compareByAge(Person a, Person b){
        return 1;
    }

    public static List<Person> createRoster(){

        return null;
    }
}
