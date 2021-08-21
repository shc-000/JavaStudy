package com.frog.study.collection;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.HashedMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static void main(String[] args) {

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
