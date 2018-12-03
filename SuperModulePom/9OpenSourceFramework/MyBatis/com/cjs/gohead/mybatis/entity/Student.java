package com.cjs.gohead.mybatis.entity;

/**
 * Created by chenjingshuai on 17-1-23.
 */
public class Student {
    private String name;
    private int age;
    private Sex sex;

    public Student(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public enum Sex {
        male, female,;
    }
}
