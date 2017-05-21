package com.cjs.gohead.mybatis.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjingshuai on 17-1-23.
 */
public class Student extends BasicEntity implements Serializable {
    private String name;
    private Sex sex;
    private List<Course> courses = new ArrayList<>();

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public enum Sex {
        male, female,;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex=" + sex.getClass().getName() +
                ", courses=" + courses +
                '}';
    }
}
