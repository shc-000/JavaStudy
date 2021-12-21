package com.frog.study.functionInterface;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * java.util.function.Predicate<T>对某一种数据类型进行判断, 结果返回一个boolean值
 *
 * @author shaohaichao
 * @since 2021/12/21
 */
public class PredicateTest {


    /**
     * 定义一个方法
     * 参数传递一个String类型的字符串
     * 传递一个Predicate接口, 泛型使用String
     * 使用predicate中的方法test对字符串进行判断, 将判断的结果返回
     *
     * @return boolean
     */
    public static boolean checkString(String s, Predicate<String> pre) {
        return pre.test(s);
    }

    /**
     * 定义一个方法,方法的参数,传递一个字符串
     * 传递两个Predicate接口
     * 一个用于判断字符串的长度是否大于5
     * 一个用于判断字符串中是否包含a
     * 两个条件必须同时满足
     *
     * @return boolean
     */
    public static boolean checkString(String s, Predicate<String> pred1, Predicate<String> pred2) {
//        默认方法:and
//        两个条件必选同时满足
        return pred1.and(pred2).test(s);
        // 等价于return pre1.test(s) && pre2.test(s);
    }

    /**
     * 定义一个方法,方法的参数,传递一个字符串
     * 传递两个Predicate接口
     * 一个用于判断字符串的长度是否大于5
     * 一个用于判断字符串中是否包含a
     * 一个条件必须满足
     *
     * @return boolean
     */
    public static boolean checkStringOr(String s, Predicate<String> pred1, Predicate<String> pred2) {

        return pred1.or(pred2).test(s);
        // 等价于return pre1.test(s) || pre2.test(s);
    }

    /**
     * 定义一个方法,方法的参数,传递一个字符串
     * 使用Predicate接口判断字符串的长度是否大于5
     *
     * @return boolean
     */
    public static boolean checkStringNegate(String s, Predicate<String> pre) {
        //return !pre.test(s);
        //默认方法: negate(去反)
        return pre.negate().test(s);
        //等效于return !pre.test(s);
    }


    /**
     *练习：集合信息筛选
     *     数组当中有多条“姓名+性别”的信息如下，
     *     String[] array = { "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男", "赵丽颖,女" };
     *     请通过Predicate接口的拼装将符合要求的字符串筛选到集合ArrayList中，
     *     需要同时满足两个条件：
     *         1. 必须为女生；
     *         2. 姓名为4个字。
     *
     *     分析:
     *         1.有两个判断条件,所以需要使用两个Predicate接口,对条件进行判断
     *         2.必须同时满足两个条件,所以可以使用and方法连接两个判断条件
     */
    /**
     * 定义一个方法
     * 方法的参数传递一个包含人员信息的数组
     * 传递两个Predicate接口,用于对数组中的信息进行过滤
     * 把满足条件的信息存到ArrayList集合中并返回
     *
     * @return java.util.ArrayList<java.lang.String>
     */
    public static ArrayList<String> filterTest(String[] arr, Predicate<String> pre1, Predicate<String> pre2) {
        //定义一个ArrayList集合,存储过滤之后的信息
        ArrayList<String> list = new ArrayList<>();
        //遍历数组,获取数组中的信息
        for (String s : arr) {
            //使用Predicate接口中的方法test对获取的字符串进行判断
            boolean b = pre1.and(pre2).test(s);
            //对布尔值进行判断
            if (b) {
                //存储到list中
                list.add(s);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String s = "abcdef";
        //对参数传递的字符串进行判断,判断字符串的长度是否大于5,并把判断的结果返回
        boolean b1 = checkString(s, str -> str.length() > 5);
        System.out.println(b1);

        // TODO-SHC: 2021/12/21 方法2
        // 调用方法,进行判断
        boolean b2 = checkString(s, (String str) -> {
            return str.length() > 5;
        }, (String str) -> {
            return str.contains("a");
        });
        System.out.println(b2);

        // TODO-SHC: 2021/12/21 方法3
        // 调用方法,进行判断
        boolean b3 = checkStringOr(s, (String str) -> {
            return str.length() > 5;
        }, (String str) -> {
            return str.contains("a");
        });
        System.out.println(b3);

        // TODO-SHC: 2021/12/21 方法4
        boolean b4 = checkStringNegate(s, (String str) -> {
            //判断字符串的长度是否大于5,并返回结果
            return str.length() > 5;
        });
        System.out.println(b4);

        // TODO-SHC: 2021/12/21 方法5
        //定义一个存储字符串的数组
        String[] array = {"迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男", "赵丽颖,女"};
        ArrayList<String> list = filterTest(array, (String s5) -> {
            return s.split(",")[1].equals("女");
        }, (String s5) -> {
            return s.split(",")[0].length() == 4;
        });
        //遍历集合
        for (String s5 : list) {
            System.out.println(s);
        }

    }
}
