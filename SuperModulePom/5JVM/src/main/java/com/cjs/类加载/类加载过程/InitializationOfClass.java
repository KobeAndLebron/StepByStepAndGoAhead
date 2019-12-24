package com.cjs.类加载.类加载过程;

/**
 * 类的初始化时机及顺序.
 *
 * 先初始化启动类.
 */
public class InitializationOfClass extends Parent {
    public static void main(String[] args) {
        staticFunction();


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("被动引用:   ");
        // 三种被动引用的情况, 都不会触发类的初始化.
        // 通过数组定义来引用类
        PassivityReference[] passivityReferences = new PassivityReference[10];
        // 访问类的常量.
        System.out.println("访问类的常量, 不会初始化类: " + PassivityReference.INT);
        // 访问父类的静态变量
        System.out.println("访问父类的静态变量, 不会初始化子类: " + PassivityReference.amount);


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("访问特殊的静态常量(由function计算得到), 也会触发类的初始化.");
        System.out.println(PassivityReference.CONSTANT_BY_FUNCITON);
    }

    // 自顶向下执行.
    // 2. 父类的<clinit>()方法会优于子类执行, 由JVM保证.
    static InitializationOfClass book = new InitializationOfClass();

    static {
        // 5. <Clinit>方法自顶向下执行.
        System.out.println("5. <Clinit>方法自顶向下执行." + " 书的静态代码块");
    }

    {
        System.out.println("2. 非静态代码块优于构造器执行: 书的普通代码块"); // 2
    }

    InitializationOfClass() {
        // 隐藏调用父类的构造器. super()
        System.out.println("构造器: 书的构造方法"); // 3
        System.out.println("构造器: price=" + price + ",amount=" + amount); // 4
    }

    public static void staticFunction() {
        System.out.println("6: 书的静态方法"); // 6
    }

    int price = 110;
    static int amount = 112;

    private static class PassivityReference extends InitializationOfClass {

        public static final int INT = 1;

        static {
            System.out.println("PassivityReference被初始化");
        }

        static final String CONSTANT_BY_FUNCITON = "aaa" + getCCC();

        private static String getCCC() {
            return "ccc";
        }
    }
}

class Parent {
    static {
        // 1. 父类的<clinit>()方法会优于子类执行, 由JVM保证.
        System.out.println("1. 父类的<clinit>()方法会优于子类执行, 由JVM保证." + ": 爸爸的静态代码块");
    }
}