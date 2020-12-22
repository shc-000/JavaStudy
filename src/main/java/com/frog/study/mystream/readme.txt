相信很多人在使用过java8的streamAPI接口之后，都会对其实现原理感到好奇，但往往在看到jdk的stream源码后却被其复杂的抽象、封装给弄糊涂了，而无法很好的理解其背后的原理。究其原因，是因为jdk的stream源码是高度工程化的代码，工程化的代码为了效率和满足各式各样的需求，会将代码实现的极其复杂，不易理解。

在这里，我们将抛开jdk的实现思路，从零开始实现一个stream流。

我们的stream流同样拥有惰性求值，函数式编程接口等特性，并兼容jdk的Collection等数据结构(但不支持并行计算 orz)。

相信在亲手实现一个stream流的框架之后，大家能更好的理解流计算的原理。

在探讨探究stream的实现原理和动手实现之前，我们先要体会stream流计算的独特之处。

　　举个例子： 有一个List<Person>列表,我们需要获得年龄为70岁的前10个Person的姓名。

过程式的解决方案：

　　稍加思考，我们很快就写出了一个过程式的解决方案(伪代码)：

List<Person> personList = fromDB(); // 获得List<Person>
int limit = 10; // 限制条件
List<String> nameList = new ArrayList(); // 收集的姓名集合
for(Person personItem : personList){
    if(personItem.age == 70){ // 满足条件
        nameList.add(personItem.name); // 加入姓名集合
        if(nameList.size() >= 10){ // 判断是否超过限制
            break;
        }
    }
}
return nameList;

函数式stream解决方案：

　　下面我们给出一种基于stream流的解决方案(伪代码)：

List<Person> personList = fromDB(); // 获得List<Person>
List<String> nameList = personList.stream()
　　　　　　.filter(item->item.age == 70) // 过滤条件
　　　　　　.limit(10)    // limit限制条件
　　　　　　.map(item->item.name) // 获得姓名
　　　　　　.collect(Collector.toList()); // 转化为list

return nameList;

两种方案的不同之处：
　　从函数式的角度上看，过程式的代码实现将收集元素、循环迭代、各种逻辑判断耦合在一起，暴露了太多细节。当未来需求变动和变得更加复杂的情况下，过程式的代码将变得难以理解和维护(需要控制台打印出 年龄为70岁的前10个Person中，姓王的Person的名称）。
　　函数式的解决方案解开了代码细节和业务逻辑的耦合，类似于sql语句，表达的是"要做什么"而不是"如何去做"，使程序员可以更加专注于业务逻辑，写出易于理解和维护的代码。

List<Person> personList = fromDB(); // 获得List<Person>
personList.stream()
    .filter(item->item.age == 70) // 过滤条件
    .limit(10)    // limit限制条件
    .filter(item->item.name.startWith("王"))  // 过滤条件
    .map(item->item.name) // 获得姓名
    .forEach(System.out::println);