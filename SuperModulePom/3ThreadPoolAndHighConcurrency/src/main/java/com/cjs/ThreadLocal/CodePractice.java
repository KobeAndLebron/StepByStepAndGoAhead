package com.cjs.ThreadLocal;

import java.util.Objects;
import java.util.Stack;

public class CodePractice {
    /**
     * 在有多个子线程的情况下使用, 在不使用同步的情况下保证每个线程内code的顺序.
     */
    private static final ThreadLocal<Stack<Integer>> codesThreadLocal = new ThreadLocal<Stack<Integer>>(){
        @Override
        protected Stack<Integer> initialValue() {
            return new Stack<>();
        }
    };

    /**
     * 用于父线程中, 将此ThreadLocalMap变量对应的Stack<Integer>传递到子类, 以便共同使用.
     */
    private static final InheritableThreadLocal<Stack<Integer>> inheritableCodesThreadLocals = new InheritableThreadLocal<Stack<Integer>>(){
        @Override
        protected Stack<Integer> initialValue() {
            return new Stack<>();
        }

        /*@Override
        protected Stack<Integer> childValue(Stack<Integer> parentValue) {
            return parentValue;
        }*/
    };

    public static void pushCode(Integer value){
        inheritableCodesThreadLocals.get().push(value);
    }


    public static synchronized void pushCode(Stack<Integer> stack){
        for (Integer i : stack) {
            inheritableCodesThreadLocals.get().push(i);
        }
    }

    /**
     * 在初始化阶段设置CodeStack, 覆盖了{@linkplain InheritableThreadLocal#initialValue()}的行为.
     * @param value
     */
    public static void setNewStack(Stack<Integer> value){
        inheritableCodesThreadLocals.set(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCodeStack(){
        return (T) inheritableCodesThreadLocals.get();
    }


    public static void pushCodeInSingleThread(Integer value){
        codesThreadLocal.get().push(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCodeStackInSingleThread(){
        return (T) codesThreadLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        // CodePractice.setNewStack(new Stack<Integer>());
        System.out.println(CodePractice.getCodeStack());
        CodePractice.pushCode(1);
        QueryThread1 t1 = new QueryThread1();
        t1.setName("queryThread1");
        QueryThread2 t2 = new QueryThread2();
        t2.setName("queryThread2");
        t1.start();
        t2.start();

        // Simple way to join two threads at the same time.
        while (t1.isAlive() || t2.isAlive()) {
            Thread.sleep(10);
        }

        CodePractice.syso();
    }

    public static void syso() {
        Stack<Integer> stack = CodePractice.getCodeStack();
        System.out.println(Thread.currentThread() + "" + stack + Objects.hash(stack));
    }
}

class QueryThread1 extends Thread {
    public void run() {

        CodePractice.pushCodeInSingleThread(1);
        CodePractice.pushCodeInSingleThread(2);
        CodePractice.pushCodeInSingleThread(3);

        Stack<Integer> s = CodePractice.getCodeStackInSingleThread();
        CodePractice.pushCode(s);

        CodePractice.syso();
    }
}


class QueryThread2 extends Thread {
    public void run() {

        CodePractice.pushCodeInSingleThread(3);
        CodePractice.pushCodeInSingleThread(4);
        CodePractice.pushCodeInSingleThread(5);

        Stack<Integer> s = CodePractice.getCodeStackInSingleThread();
        CodePractice.pushCode(s);

        CodePractice.syso();
    }
}


