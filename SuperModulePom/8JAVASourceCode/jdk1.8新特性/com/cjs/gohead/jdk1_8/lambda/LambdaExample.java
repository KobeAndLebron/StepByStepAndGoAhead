package com.cjs.gohead.jdk1_8.lambda;

import java.util.HashMap;
import java.util.function.*;

/**
 * lambda表达式好处: 不用写匿名内部类
 *
 * lambda表达式的限制: 如果一个接口里面有多个方法, 就不能用Lambda表达式.
 *
 * ------
 * lambda表达式由: (逗号分隔的参数列表) -> 函数体 三部分组成, 例: (t) -> {System.out.println(t)}; 与匿名内部类相似, 这个表达式是没有名字的.
 *
 * ------
 * {@linkplain java.util.function}包里面提供了大量的函数式接口, 是对各种Lambda表达式的一种总结, 方便了我们写Lambda表达式. 比如:
 *  1. {@linkplain Function}表示接收一个方法参数并返回一个结果的方法/lambda表达式.
 *  2. {@linkplain Consumer}表示接收一个方法参数但是无返回结果的方法/lambda表达式.
 *  3. {@linkplain BiFunction}表示接受两个方法参数并返回一个结果的方法/lambda表达式.
 *  4. {@linkplain BiConsumer}表示接收两个方法参数但是无返回结果的方法/lambda表达式.
 *  5. {@linkplain Supplier}表示无方法参数但是返回一个结果的方法/lambda表达式.
 */
public class LambdaExample {
    public static void main(String[] args) {
        // lambda表达式其实就是实现了接口内的方法, 只不过是代替了匿名内部类的方式.
        // 1. 最普通的用法
        Consumer<String> consumer = (t) -> {
            System.out.println(t);
        };
        consumer.accept("Common Lambda expression");

        // 2. 如果方法实现里面只有一行, 且这行代码的方法调用入参和lambda函数相同, 则这行代码就可以优化为方法引用(Lambda can be replaced with method reference).
        // 方法引用包括:
        // 2.1 实例对象::实例方法名
        // 2.2 类名::静态方法名
        // 2.3 类名::实例方法名

        // 2.1 实例对象::实例方法名引用
        Consumer<String> consumer1 = System.out::println;
        consumer1.accept("Lambda replaced with method reference");
        // BiConsumer有两个入参, 而println只有一个入参, 所以不能优化为方法引用.
        BiConsumer<String, String> biConsumer = (t1, t2) -> {
            System.out.println(t1 + t2);
        };
        biConsumer.accept("BiConsumer 表达式: " + "1", "2");
        // 2.2 类名::静态方法名
        Function<Long, Long> f = Math::abs;
        System.out.println("静态方法引用: " + f.apply(-1L));
        // 2.3 类名::实例方法名
        // 若Lambda表达式的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，就 可以使用这种方法
        BiPredicate<String, String> b = String::equals;
        System.out.println("BiPredicate" + b.test("1", "1"));

        // 与方法引用类似的, 还有引用构造方法和引用数组.
        // 3. 引用构造器
        Function<Integer, HashMap> int2HashMap = n -> new HashMap(n); //(Lambda can be replaced with method reference).
        Function<Integer, HashMap> int2HashMap1 = HashMap::new;
        HashMap hashMap = int2HashMap.apply(32);
        HashMap hashMap1 = int2HashMap1.apply(32);
        System.out.println("引用构造器: HashMap的大小: " + hashMap.equals(hashMap1));
        // 4. 引用数组(等同于构造器)
        Function<Integer, int[]> int2Arr = n -> new int[n];
        Function<Integer, int[]> int2Arr1 = int[]::new;
        System.out.println("引用数组: " + (int2Arr.apply(2).length == int2Arr1.apply(2).length));


        // 5. 自己的Lambda表达式.
        new LambdaExample().test(() -> {
            System.out.println("callBack");
        });
    }

    private void test(PersonCallBack callBack) {
        callBack.callBack();
    }
}
