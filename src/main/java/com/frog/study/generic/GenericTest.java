package com.frog.study.generic;


/**
 * 范型测试
 *
 * @author shaohaichao
 * @since 2021/12/7
 */
public class GenericTest {
    public static void main(String[] args) {
        CollegeStudent[] stu = new CollegeStudent[] {
                new CollegeStudent(3), new CollegeStudent(2),
                new CollegeStudent(5), new CollegeStudent(4)};
        selectionSort01(stu, 2);

        selectionSort02(stu, 3);
    }

    public <T extends Number> double calc(T t1, T t2) {
        return t1.doubleValue() + t2.doubleValue();
    }

    /**
     * <T extends Comparable<T>>规定了数组中对象必须实现Comparable接口，
     * Comparable<? Super T>表示如果父类实现Comparable接口，其自身可不实现
     */
    public static <T extends Comparable<? super T>>
    void selectionSort01(T[] a, int n) {
        System.out.println(a[n]);
    }

    public static <T extends Comparable<T>>
    void selectionSort02(T[] a, int n) {
        System.out.println(a[n]);
    }


    public static class CollegeStudent extends Student {
        public CollegeStudent(int id) {
            super(id);
        }
    }

    public static class Student implements Comparable<Student> {
        private int id;

        public Student(int id) {
            this.id = id;
        }

        @Override
        public int compareTo(Student o) {
            return (id > o.id) ? 1 : ((id < o.id) ? -1 : 0);
        }
    }
}
