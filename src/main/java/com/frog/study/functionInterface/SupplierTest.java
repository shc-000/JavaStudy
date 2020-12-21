package com.frog.study.functionInterface;

import java.util.function.Supplier;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2020/12/21 10:15 下午
 */
public class SupplierTest {
//    java.util.function.Supplier<T>接口仅包含一个无参的方法:T get().用来获取一个泛型参数指定类型的对象数据.
//            由于这是一个函数式接口,这就意味着对应的Lambda表达式需要"对外提供"一个符合泛型对象数据
    private static String getString(Supplier<String> function){
        return function.get();
    }
    public static void supplier(){
        String msg1 = "hello";
        String msg2 = "world";
        System.out.println(getString(() -> msg1+msg2));
    }

    public static void main(String[] args) {
        supplier();
        max();
    }

    //使用Supplier接口作为方法参数类型, 通过Lambda表达式求出int数组中最大值; 接口的泛型使用java.lang.Integer.
    // 定义一个方法,方法的参数传递Supplier,泛型使用Integer
    public static int getMax(Supplier<Integer> sup){
        return sup.get();
    }
    public static void max(){
        int arr[] = {1, 2, 5, 6, 8};
        // 调用getMax的方法,参数传递lambda
        int maxNum = getMax(() ->{
            //计算数组的最大值
            int max = arr[0];
            for(int i: arr){
                if(i>max){
                    max=i;
                }
            }
            return max;
        });
        System.out.println(maxNum);
    }
}
