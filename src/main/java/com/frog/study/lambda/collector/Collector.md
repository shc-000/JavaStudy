Collector<T, A, R> 接口
Collector Interface 包含一系列方法，为实现具体的规约操作提供了范本。
我们可以通过实现Collector接口来自定义自己的收集器，从而可以自由地创建自定义规约操作。
要想自定义收集器，必然需要先理解Collector接口的定义。
其中接口泛型类定义如下：
-T是流中要收集的项目的泛型 。
-A是累加器的泛型，累加器在收集过程中用于累积部分结果。
-R是收集操作得到的对象的类型（通常是集合）。

Collector Interface 定义如下:

```
public interface Collector<T, A, R> {
    
    Supplier<A> supplier();

    BiConsumer<A, T> accumulator();

    BinaryOperator<A> combiner();

    Function<A, R> finisher();

    Set<Characteristics> characteristics();

    /**
     * Characteristics indicating properties of a {@code Collector}, which can
     * be used to optimize reduction implementations.
     */
    enum Characteristics {
        CONCURRENT,

        UNORDERED,

        IDENTITY_FINISH
    }
}
```

1. 建立新的结果容器（容器的提供者）：supplier方法
supplier方法需返回一个Supplier，也就是一个无参数函数。
在调用时会创建一个空的累加器实例，供数据收集过程使用。
如Collectors.toList()中supplier实现为:
return ArrayList:new;

2. 将元素添加到结果容器（累计操作）：accumulator方法
accumulator方法会返回执行规约操作的函数，每次执行函数都会更新累加器。
BiConsumer 无返回值，原位更新累加器。两个参数分别为保存规约结果的累加器和遍历元素。
如Collectors.toList()中accumulator方法实现为：
return List:add;
   
3. 对结果容器应用最终转换（终止操作）：finisher方法
这是在遍历完流之后，在累积过程的最后要调用的一个函数，以便将累加器对象转换为整个集合操作的最终结果。
通常，累加器对象便是最终结果。如Collectors.toList()方法中finisher实现为：
return List:addAll;

4. 合并两个结果容器（并发的情况将每个线程的中间容器A合并）：combiner方法
返回一个供规约操作使用的函数，定义了对流的各个子部分进行并行处理时，各个子部分要如何合并。
即将多个累加器合并为一个，如Collectors.toList()中combiner实现为:
return List:addALL

5. 定义收集器的行为（收集器特性）：characteristics方法
返回一份不可变的Characteristic集合，它定义了收集器的行为——关于流是否可以进行并行规约、可以使用那些优化的提示。总分包含三个部分：
- UNORDERED——规约结果不受流中项目的遍历和累积顺序的影响
- CONCURRENT——accumulator函数可以从多个线程同时调用，且该收集器可以并行规约流。
- IDENTITY_FINSIH——表示完成器方法返回的函数是一个恒等函数，可以跳过。这种情况下，
累加器对象将会直接用作规约过程的最终结果。即不会将累加器A转化为结果R。
```
enum Characteristics {
    /**
     * 如果一个收集器被标记为concurrent特性，那么accumulator 方法可以被多线程并发的的调用，
     * 并且只使用一个容器A.如果收集器被标记为concurrent，但是要操作的集合是有序的，那么
     * 最终得到的结果不能保证原来的顺序
     */
    CONCURRENT,

    /**
     * 适用于无序的集合
     */
    UNORDERED,

    /**
     * 如果收集器特性被设置IDENTITY_FINISH，那么会强制将中间容器A类型转换为结果类型R
     */
    IDENTITY_FINISH
}
```