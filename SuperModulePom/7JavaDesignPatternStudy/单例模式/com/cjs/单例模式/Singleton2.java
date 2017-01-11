package com.cjs.单例模式;

public class Singleton2 {
    private static final String CONSTANT = "A";

    private Singleton2(){
        System.out.println("Singleton2 is loaded.");
    }

	private static class Temp{
		static Singleton2 singleton = new Singleton2();
	}

	public static Singleton2 getSingleton(){
		return Temp.singleton;
	}

    /**
     * gains 静态类不会随着类的加载而加载, 只会在第一次使用的时候进行加载.
     *  相比于{@linkplain Singleton1} 懒汉式的一种方式
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(CONSTANT);
    }
}
