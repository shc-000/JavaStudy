package com.frog.study.stream;

import com.frog.study.base.Car;
import com.frog.study.base.Insurance;
import com.frog.study.base.OptionalPerson;
import com.frog.study.base.Person;

import java.util.Objects;
import java.util.Optional;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/18 10:25 下午
 */
public class OptionalTest {
    public static void main(String[] args) {

    }

    /**
     * 通常获取一个属性需要经过层层判空的操作
     */
    private static String getInsuranceName(Person person) {
        if (Objects.nonNull(person)) {
            Car car = person.getCar();
            if (Objects.nonNull(car)) {
                Insurance insurance = car.getInsurance();
                if (Objects.nonNull(insurance)) {
                    return insurance.getName();
                }
            }
        }
        return "default name";
    }

    /**
     * 使用Optional之后可以这样简洁
     */
    private static String getInsuranceName2(OptionalPerson person) {
        return Optional.ofNullable(person)
                .flatMap(OptionalPerson::getCar)
                .flatMap(OptionalPerson.Car::getInsurance)
                .map(OptionalPerson.Car.Insurance::getName)
                .orElse("default name");
    }

    /**
     * 普调
     */
    private static String getName(Insurance insurance) {
        if (Objects.nonNull(insurance)) {
            return insurance.getName();
        }
        return "default name";
    }

    /**
     * Optional
     */
    private static String getName2(Insurance insurance) {
        return Optional.ofNullable(insurance).map(Insurance::getName).orElse("default name");
    }
}
