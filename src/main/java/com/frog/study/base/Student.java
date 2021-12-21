package com.frog.study.base;

import lombok.*;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since  2020/12/15 9:22 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Student {
    private String name;
    private int age;
    private String sex;
    private String school;
}
