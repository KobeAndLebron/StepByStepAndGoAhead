package com.cjs.gohead.mybatis.client;

import com.cjs.gohead.mybatis.entity.Course;
import com.cjs.gohead.mybatis.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjingshuai on 17-1-23.
 */
public class Client {

    /*public static void main(String args[]) throws IOException {

        Reader reader = Resources.getResourceAsReader("mybatis/masterCon.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        //Create a new student object
        Student student = new Student("Mohammad");
        student.setSex(Student.Sex.female);

        //Insert student data
        session.insert("Student.insert", student);
        System.out.println("record inserted successfully");

        Course course1 = new Course("Algorithm");
        Course course2 = new Course("Operation");
        Course course3 = new Course("Math");
        student.addCourse(course1);
        student.addCourse(course2);
        student.addCourse(course3);

        for (Course course : student.getCourses()) {
            session.insert("Course.insert", course);
            Map<String, String> map = new HashMap<>();
            map.put("cId", course.getId());
            map.put("sId", student.getId());
            session.insert("Student.insertStudent_Course", map);
        }

        session.commit();
        session.close();
    }*/


    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis/masterCon.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        Student student = new Student("Mohammad");
        System.out.println(session.selectOne("Student.select", student));
    }
}
