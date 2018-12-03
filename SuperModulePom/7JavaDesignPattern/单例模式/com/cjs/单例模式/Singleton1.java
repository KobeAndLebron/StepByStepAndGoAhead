package com.cjs.单例模式;

/**
 * 单例模式的第一种写法:饿汉式
 *	缺点：无论我们是否调用单例类的静态方法，在内存中都会存在有它的唯一实例
 *	优点：没有懒汉式的多线程问题
 * @author 陈景帅
 *
 * 每天进步一点——2016年2月24日
 *
 */
public class Singleton1 {
    private static final String CONSTANT = "A";
	private static Singleton1 singleton = new Singleton1();
	
	private Singleton1(){
        System.out.println("Singleton1 is loaded.");
	}
	
	public static Singleton1 getSingleton(){
		return Singleton1.singleton;
	}

    /**
     * gains 静态变量会随着类的加载而完成. {@linkplain Singleton2#main(String[])}
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(CONSTANT);
    }
}
