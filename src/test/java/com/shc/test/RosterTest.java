package com.shc.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @see -https://zhuanlan.zhihu.com/p/102716267
 * @since 2021/1/4
 */
public class RosterTest {
    interface CheckPerson {
        boolean test(Person p);
    }

    /**
     * 1. eg：输出年龄大于20岁的成员
     */
    public static void printPersonsOlderThan(List<Person> roster, int age) {
        for (Person p : roster) {
            if (p.getAge() >= age) {
                p.printPerson();
            }
        }
    }

    /**
     * 2. eg:输出年龄在14到30岁之间的成员
     * 更全面的匹配方法
     * 如果老板只要男性成员呢？
     */
    public static void printPersonsWithinAgeRange(
            List<Person> roster, int low, int high) {
        for (Person p : roster) {
            if (low <= p.getAge() && p.getAge() < high) {
                p.printPerson();
            }
        }
    }

    /**
     * 3. eg:老板又提出了各种复杂的需求，不要处女座的、只要邮箱是163的，怎么搞？
     * 方法1：在本地类中指定搜索条件代码，通过接口方式，不同的需求对应不同的实现类，
     * 每次都要新建实现类，写大量的代码
     * 方法2：在匿名类中指定搜索条件代码，不需要写各种实现，但是还要写个interface CheckPerson，
     * 而且匿名类写起来也挺麻烦
     * 方法3：Lambda表达式是懒人的不二之选，CheckPerson是一个只包含一个抽象方法的接口，
     * 比较简单，Lambda可以省略其实现
     */
    public static void printPersons(
            List<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    /**
     * 4. eg: 搞这么久，还得写一个接口，而且是只有一个抽象方法，还是不爽？
     * 你也可以使用标准的函数接口来代替接口CheckPerson，从而进一步减少所需的代码量
     * java.util.function包中定义了标准的函数接口
     * 我们可以使用JDK8提供的 Predicate<T>接口来代替CheckPerson。
     * 该接口包含方法boolean test(T t)
     */
    public static void printPersonsWithPredicate(
            List<Person> roster, Predicate<Person> tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    /**
     * 5. Lambda表达式可不只是能够简化匿名类
     * 简化 p.printPerson(),
     * 使用Consumer<T>接口的void accept(T t)方法，相当于入参的操作
     */
    public static void processPersons(
            List<Person> roster,
            Predicate<Person> tester,
            Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

    /**
     * 6. eg: 老板说了只想看到邮箱
     * Function<T,R>接口，相当于输入类型，mapper定义参数，block负责方对给定的参数进行执行
     */
    public static void processPersonsWithFunction(
            List<Person> roster,
            Predicate<Person> tester,
            Function<Person, String> mapper,
            Consumer<String> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                String data = mapper.apply(p);
                block.accept(data);
            }
        }
    }

    // 7. 使用泛型
    public static <X, Y> void processElements(
            Iterable<X> source,
            Predicate<X> tester,
            Function<X, Y> mapper,
            Consumer<Y> block) {
        for (X p : source) {
            if (tester.test(p)) {
                Y data = mapper.apply(p);
                block.accept(data);
            }
        }
    }

    public static void main(String[] args) {
        List<Person> roster = Person.createRoster();

        /**
         * 1. 输出年龄大于20岁的成员
         */
        System.out.println("Persons older than 20:");
        printPersonsOlderThan(roster, 20);
        System.out.println();

        /**
         * 2. 输出年龄在14到30岁之间的成员
         */
        System.out.println("Persons between the ages of 14 and 30:");
        printPersonsWithinAgeRange(roster, 14, 30);
        System.out.println();

        /**
         * 3. 输出年龄在18到25岁的男性成员
         * （在本地类中指定搜索条件）
         *   您可以使用一个匿名类而不是一个本地类，并且不必为每个搜索声明一个新类
         */
        System.out.println("Persons who are eligible for Selective Service:");
        class CheckPersonEligibleForSelectiveService implements CheckPerson {
            public boolean test(Person p) {
                return p.getGender().equals("MALE")
                        && p.getAge() >= 18
                        && p.getAge() <= 25;
            }
        }

        // 这个其实就是通过行为参数化传递代码
        printPersons(
                roster, new CheckPersonEligibleForSelectiveService());

        System.out.println();

        // 3. 在匿名类中指定搜索条件代码
        System.out.println("Persons who are eligible for Selective Service " +
                "(anonymous class):");
        printPersons(
                roster,
                new CheckPerson() {
                    public boolean test(Person p) {
                        return p.getGender().equals("MALE")
                                && p.getAge() >= 18
                                && p.getAge() <= 25;
                    }
                }
        );

        System.out.println();

        // 3: 使用Lambda表达式简化代码，一个箭头
        System.out.println("Persons who are eligible for Selective Service " +
                "(lambda expression):");

        printPersons(
                roster,
                (Person p) -> p.getGender().equals("MALE")
                        && p.getAge() >= 18
                        && p.getAge() <= 25
        );

        System.out.println();

        // 4. 使用Lambda的标准功能接口
        System.out.println("Persons who are eligible for Selective Service " +
                "(with Predicate parameter):");

        printPersonsWithPredicate(
                roster,
                p -> p.getGender().equals("MALE")
                        && p.getAge() >= 18
                        && p.getAge() <= 25
        );

        System.out.println();

        //5.使用Predicate和Consumer参数
        System.out.println("5. Persons who are eligible for Selective Service " +
                "(with Predicate and Consumer parameters):");

        processPersons(
                roster,
                p -> p.getGender().equals("MALE")
                        && p.getAge() >= 18
                        && p.getAge() <= 25,
                p -> p.printPerson()
        );

        System.out.println();

        // 6. 通过Function<T,R> 指定输出类型
        System.out.println("Persons who are eligible for Selective Service " +
                "(with Predicate, Function, and Consumer parameters):");

        processPersonsWithFunction(
                roster,
                p -> p.getGender().equals("MALE")
                        && p.getAge() >= 18
                        && p.getAge() <= 25,
                p -> p.getEmailAddress(),
                email -> System.out.println(email)
        );

        System.out.println();

        // 7. 使用泛型
        System.out.println("Persons who are eligible for Selective Service " +
                "(generic version):");

        processElements(
                roster,
                p -> p.getGender().equals("MALE")
                        && p.getAge() >= 18
                        && p.getAge() <= 25,
                p -> p.getEmailAddress(),
                email -> System.out.println(email)
        );

        System.out.println();

        // 8: 使用接受Lambda表达式的批量数据操作
        System.out.println("Persons who are eligible for Selective Service " +
                "(with bulk data operations):");

        roster.stream()
                .filter(
                        p -> p.getGender().equals("MALE")
                                && p.getAge() >= 18
                                && p.getAge() <= 25)
                .map(p -> p.getEmailAddress())
                .forEach(email -> System.out.println(email));
        System.out.println();

        /**
         *  9. 按年龄排序。Java 8 之前需要实现 Comparator 接口
         *  接口比较器是一个功能接口。因此，
         *  可以使用lambda表达式来代替定义并创建一个实现了Comparator的类的新实例:
         */
        Person[] rosterAsArray = roster.toArray(new Person[roster.size()]);

        Arrays.sort(rosterAsArray,
                (a, b) -> Person.compareByAge(a, b)
        );

        for (Person person : roster) {
            person.printPerson();
        }

        /**
         *  这种比较两个Person实例的出生日期的方法已经作为Person.
         *	comparebyage存在。你可以在lambda表达式中调用这个方法
         */

        Arrays.sort(rosterAsArray,
                (a, b) -> Person.compareByAge(a, b)
        );

        /**
         *  ===================================================================
         *  方法引用：
         * 这个lambda表达式调用现有的方法，所以您可以使用方法引用而不是lambda表达式
         *  Person::compareByAge 等同于 (a, b) -> Person.compareByAge(a, b)
         */
        Arrays.sort(rosterAsArray, Person::compareByAge);
        System.out.println();

        // Reference to an Instance Method of a Particular Object
        class ComparisonProvider {
            public int compareByName(Person a, Person b) {
                return a.getName().compareTo(b.getName());
            }

            public int compareByAge(Person a, Person b) {
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }
        ComparisonProvider myComparisonProvider = new ComparisonProvider();
        Arrays.sort(rosterAsArray, myComparisonProvider::compareByName);
        for (Person person : rosterAsArray) {
            person.printPerson();
        }

        System.out.println();

        /**
         * 引用特定类型的任意对象的实例方法
         * String::compareToIgnoreCase 格式化 (String a, String b) ，
         *  并去调用 a.compareToIgnoreCase(b)
         */
        String[] stringArray = {"Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda"};
        Arrays.sort(stringArray, String::compareToIgnoreCase);
        for (String s : stringArray) {
            System.out.println(s);
        }

        System.out.println();

        // 通过 stream 将计算集合的和
        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8};
        List<Integer> listOfIntegers =
                new ArrayList<>(Arrays.asList(intArray));
        System.out.println("Sum of integers: " +
                listOfIntegers
                        .stream()
                        .reduce(Integer::sum).get());
    }
}
