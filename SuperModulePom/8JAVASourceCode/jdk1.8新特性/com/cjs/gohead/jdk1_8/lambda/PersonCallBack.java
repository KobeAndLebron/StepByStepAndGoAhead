package com.cjs.gohead.jdk1_8.lambda;

/**
 * {@linkplain FunctionalInterface}是一个函数式接口的注解声明, 被这个注解注解的接口, 可以用Lambda表达式代替匿名内部类来表达这个接口.
 *
 * 被{@linkplain FunctionalInterface}注解的接口只能有一个抽象方法(不包括默认的方法), 否则编译会报错; 如果接口没有被@FunctionalInterface
 * 注解, 那么当接口有超过1个抽象方法的时候, 也不能使用lambda表达式.
 *
 * 函数式接口包括:
 *   1. lambda表达式
 *   2. 方法引用
 *   3. 构造器引用
 *   > 方法引用和构造器引用是语法简化后的lambda表达式.
 */
@FunctionalInterface
public interface PersonCallBack {
    void callBack();
    // 编译会报错.
    // void callBack1();

    // Harvest: JDK1.8里面新增, 接口里面可以由默认的方法实现. 在没有这个特性之前, 当在接口新增方法的时候, 是在抽象类里面加默认的方法实现的.
    default void callBack4() {
        System.out.println("I am the first default method in interface.");
    }

    default void callBack5() {
        System.out.println("I am the second default method in interface.");
    }

}
