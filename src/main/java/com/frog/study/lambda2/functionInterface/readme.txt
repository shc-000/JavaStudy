* Java8 内置的四⼤大核⼼心函数式接⼝口
Consumer<T> : 消费型接⼝口 void accept(T t);
Supplier<T> : 供给型接⼝口 T get();
Function<T, R> : 函数型接⼝口 R apply(T t);
Predicate<T> : 断⾔言型接⼝口 boolean test(T t);