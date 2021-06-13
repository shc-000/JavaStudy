package com.frog.study.jvm;

/**
 * 在java8中，永久代已經被移除，被元空間取代。元空间本质和永久代类似。
 *
 * 元空间（java8）与永久代（java7）之间最大的区别在于：
 *
 * 永久代使用的是JVM堆内存，但是java8以后的元空间并不在虚拟机中，而是使用本机物理内存。
 *
 * 因此，默认情况下，元空间大小受本地内存限制。类的元数据放入native memory，字符串池和类的静态变量放入java堆，这样可以加载多少类的元数据就不再由MaxPermSize控制，而是由系统实际可用空间决定。
 *
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/06/02
 */
public class InitailHeapSizeDemo {
    /**
     * -Xms 堆内存初始大小，默认内存大小：系统64/1
     * -Xmx 堆内存最大值，默认内存大小：系统4/1
     * -Xmn 堆内存年轻代大小
     */
    public static void main(String[] args) {
        long initialHeapSize = Runtime.getRuntime().totalMemory();
        long maxHeapSize = Runtime.getRuntime().maxMemory();
        System.out.println("-Xms = " + initialHeapSize / 1024 + "kb or " + initialHeapSize / 1024 / 1024 + "mb");
        System.out.println("-Xmx = " + maxHeapSize / 1024 + "kb or " + maxHeapSize / 1024 / 1024 + "mb");
    }
}
