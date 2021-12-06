package com.frog.study.functionInterface;

/**
 * 妙用Java 8中的 Function接口 消灭if……else
 *
 * 创建工具类VUtils并创建一个isTure方法，方法的返回值为刚才定义的函数式接口-ThrowExceptionFunction。
 * ThrowExceptionFunction的接口实现逻辑为当参数b为true时抛出异常
 *
 * @author shaohaichao
 * @since 2021/12/6
 */
public class VUtils {
    /**
     * 如果参数为true抛出异常
     *
     * @param b true/false
     * @return com.example.demo.func.ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isTure(boolean b) {

        return (errorMessage) -> {
            if (b) {
                throw new RuntimeException(errorMessage);
            }
        };
    }


    //使用方式
    //调用工具类参数参数后，调用函数式接口的throwMessage方法传入异常信息。
    public static void main(String[] args) {
        // 当输入的参数为false时正常执行
        VUtils.isTure(false).throwMessage("要跑出异常拉");
        //当输入的参数为true时抛出异常
        VUtils.isTure(true).throwMessage("要跑出异常l");


//        参数为true时，执行trueHandle
//        参数为false时，执行falseHandle
        VUtils.isTureOrFalse(false).trueOrFalseHandle(() -> {
            System.out.println("false,处理相应的业务了");
        }, () -> {
            System.out.println("true,处理相应的业务了");
        });


//        调用工具类参数参数后，调用函数式接口的presentOrElseHandle方法传入一个Consumer和Runnable
        //参数不为空时，打印参数
        VUtils.isBlankOrNoBlank("test").presentOrElseHandle(System.out::println, () -> {
            System.out.println("空字符串");
        });
    }


    /**
     * 创建一个名为isTureOrFalse的方法，方法的返回值为刚才定义的函数式接口-BranchHandle。
     * 参数为true或false时，分别进行不同的操作
     *
     * @param b true/false
     * @return com.example.demo.func.BranchHandle
     **/
    public static BranchHandle isTureOrFalse(boolean b) {

        return (trueHandle, falseHandle) -> {
            if (b) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }


    /**
     * 创建一个名为isBlankOrNoBlank的方法，方法的返回值为刚才定义的函数式接口-PresentOrElseHandler。
     * 参数为true或false时，分别进行不同的操作
     *
     * @param str str
     * @return com.example.demo.func.BranchHandle
     **/
    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str) {

        return (consumer, runnable) -> {
            if (str == null || str.length() == 0) {
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }

}
