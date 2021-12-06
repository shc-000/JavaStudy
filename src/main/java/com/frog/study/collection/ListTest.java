package com.frog.study.collection;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/05/13
 */
public class ListTest {

    //list表变tree格式，list里的对象需要有三个东西 ： 本身的ID ,父级的ParentID , 以及一个子集list 列表。
    public static <T> List<T> toTree(List<T> data,
                                     Function<T, Object> getIdFunction,
                                     Function<T, Object> getParentIdFunction,
                                     BiConsumer<T, List<T>> addChildFunction) {
        if(data == null){
            //throw new ListToTreeException("树目标节点为空");
        }
        List<T> result = new ArrayList<>();
        Map<String, List<T>> tmpMap = new HashMap<>();
        //按“parentId值”分组
        for (T record : data) {
            Object parentIdValue = getParentIdFunction.apply(record);
            if (parentIdValue == null) {
                result.add(record);
            } else {
                String parentId = String.valueOf(parentIdValue);
                if (!tmpMap.containsKey(parentId)) {
                    tmpMap.put(parentId, new ArrayList<>());
                }
                tmpMap.get(parentId).add(record);
            }
        }
        //正式处理
        for (T record : data) {
            String idValue = String.valueOf(getIdFunction.apply(record));
            if (tmpMap.containsKey(idValue)) {
                addChildFunction.accept(record, tmpMap.get(idValue));
                tmpMap.remove(idValue);
            }
        }
        if (tmpMap.size() > 0) {
            //throw new ListToTreeException("以下节点，存在上下级断档，无法正常显示[" + tmpMap + "]");
        }
        return result;
    }

    public static void indexOf() {
        List<String> list = Lists.newArrayList("a", "b");
        int coo = list.indexOf("c");
        System.out.println("坐标" + coo);
    }

   static Date getLastDayOfYear(final Date date) {

        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        final int last = cal.getActualMaximum(Calendar.DAY_OF_YEAR);

        cal.set(Calendar.DAY_OF_YEAR, last);

        return cal.getTime();
    }


    public static void main(String[] args) {
        indexOf();
        Double[] doa = new Double[12];
        System.out.println(Optional.ofNullable(doa[1]).orElse(0D).toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(simpleDateFormat.format(getLastDayOfYear(new Date())));
//        toTree(answers,
//                CodeBean::getCode,
//                CodeBean::getParentCode,
//                CodeBean::setChildren);
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        System.out.println(list);
//        list.add(0,0);
//        System.out.println(list);
    }
}
