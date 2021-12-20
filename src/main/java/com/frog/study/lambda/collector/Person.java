package com.frog.study.lambda.collector;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/7/15 9:06 下午
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    private String name;

    private Integer age;
}