package com.frog.study.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Random;

/**
 * @author shaohaichao
 * @since 2021/11/19
 */
public class DateTest {
    public static void main(String[] args) {
        LocalDate yearFirstDay = LocalDate.now().atStartOfDay().with(TemporalAdjusters.firstDayOfYear()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monthFirstDay = LocalDate.now().atStartOfDay().with(TemporalAdjusters.firstDayOfMonth()).atZone(ZoneId.systemDefault()).toLocalDate();


        LocalDateTime localDate = LocalDateTime.now();
        DayOfWeek bucketWeek = DayOfWeek.from(localDate);
        localDate.plusDays(1);
        DayOfWeek bucketWeek2 = DayOfWeek.from(localDate);
        System.out.println(bucketWeek2);


        for (int i = 0; i < 10; i++) {
            System.out.println((double) new Random().nextInt(100));
        }
    }
}
