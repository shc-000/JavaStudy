package com.frog.study.base;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/18 10:28 下午
 */
@Builder
@Getter
public class OptionalPerson {

    private Optional<Car> car;

    @Builder
    @Getter
    public static class Car{
        private Optional<Insurance> insurance;

        /**
         * 保险
         */
        @Builder
        @Getter
        public static class Insurance{
            private String name;
        }
    }


}
