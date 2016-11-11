package com.cjs.ThreadLocal;

import com.sun.org.apache.bcel.internal.classfile.Code;

import java.util.Stack;

/**
 * 	线程局部变量，每个线程都为该线程局部变量保存相对应的值-具体保存在{@linkplain ThreadLocal}的内部类ThreadLocalMap里的Entry[]数组里面,
 * 数组的索引由对应的ThreadLocal对象里的threadLocalHashCode映射所得到（具体生成策略见源码），所以不会发生冲突（具体结合有道云笔记来学习）
 *
 * ********Benefit:将变量的作用域变为线程所有，不是多个线程共享。
 *
 * 原理:
 * ThreadLocal里面get方法：
 * 	1、先获取当前线程-Thread.currentThread();
 * 	2、获取当前线程的ThreadLocal.ThreadLocalMap，这个变量完全由ThreadLocal创建和操作，但是引用交给Thread管理。
 * 		如果map为空(则创建map)或者此ThreadLocal对象对应的Entry对象为null，就调用initialValue方法（这个方法使用的是模版方法模式，
 * 可以由子类重写改变其行为，默认返回null），然后把这个初始值放入到ThreadLocalMap的Entry数组中;
 * 	ThreadLocal values pertaining to this thread. This map is maintained
 * 		by the ThreadLocal class.
 *  ThreadLocal.ThreadLocalMap threadLocals = null;
 *  3、根据当前的线程局部变量(ThreadLocal的threadLocalHashCode)和Entry数组长度(index = key.threadLocalHashCode &
 *  	(table.length - 1))来获取此线程局部变量在当前线程的ThreadLocal.ThreadLocalGroup.Entry[]数组的索引
 *
 *  set相同
 *  特殊的是第二步-不会调用initialValue方法，如果map为空的话，则根据当前线程和set的值还有当前ThreadLocal来设置/创建map
 *
 * 	Struts2的ActionContext就是用的这种方法，此类的Integer相当于ActionContext，
 * 用这种方法就可以达到每个线程都有自己独立的ActionContext
 *
 * @author 陈景帅
 *
 */
public class CodePractice {
    private static final ThreadLocal<Stack<Integer>> integerThreadLocal1 = new ThreadLocal<Stack<Integer>>(){
        @Override
        protected Stack<Integer> initialValue() {
            return new Stack<>();
        }
    };

    private static final InheritableThreadLocal<Stack<Integer>> integerThreadLocal = new InheritableThreadLocal<Stack<Integer>>(){
        @Override
        protected Stack<Integer> initialValue() {
            return new Stack<>();
        }

        /*@Override
        protected Stack<Integer> childValue(Stack<Integer> parentValue) {
            return null;
        }*/
    };

    public static void main(String[] args) throws InterruptedException {
        System.out.println(CodePractice.getCodeStack());
        /*CodePractice.pushCode(1);*/
        QueryThread1 t1 = new QueryThread1();
        t1.setName("queryThread1");
        QueryThread2 t2 = new QueryThread2();
        t2.setName("queryThread2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + CodePractice.getCodeStack() + CodePractice.getCodeStack().hashCode());
    }

    public static void pushCode(Integer value){
        integerThreadLocal.get().push(value);
    }

    public static void pushCode(Stack<Integer> stack){
        for (Integer i : stack) {
            integerThreadLocal.get().push(i);
        }
    }

    public static void setNewStack(Stack<Integer> value){
        integerThreadLocal.set(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCodeStack(){
        return (T) integerThreadLocal.get();
    }

    public static void pushCodeInSingleThread(Integer value){
        integerThreadLocal1.get().push(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCodeStackInSingleThread(){
        return (T) integerThreadLocal1.get();
    }

}

class QueryThread1 extends Thread {
    public void run() {

        CodePractice.pushCodeInSingleThread(1);
        CodePractice.pushCodeInSingleThread(2);
        CodePractice.pushCodeInSingleThread(3);

        Stack<Integer> s = CodePractice.getCodeStackInSingleThread();
        CodePractice.pushCode(s);

        System.out.println(Thread.currentThread() + "" + CodePractice.getCodeStack() + CodePractice.getCodeStack().hashCode());
    }
}


class QueryThread2 extends Thread {
    public void run() {

        CodePractice.pushCodeInSingleThread(3);
        CodePractice.pushCodeInSingleThread(4);
        CodePractice.pushCodeInSingleThread(5);

        Stack<Integer> s = CodePractice.getCodeStackInSingleThread();
        CodePractice.pushCode(s);

        System.out.println(Thread.currentThread() + "" + CodePractice.getCodeStack() + CodePractice.getCodeStack().hashCode());
    }
}