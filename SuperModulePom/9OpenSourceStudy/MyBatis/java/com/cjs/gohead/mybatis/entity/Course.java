package com.cjs.gohead.mybatis.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenJingShuai on 2017/5/20.
 */
public class Course extends BasicEntity implements Serializable {
    private String name;
    private List<Student> students;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
