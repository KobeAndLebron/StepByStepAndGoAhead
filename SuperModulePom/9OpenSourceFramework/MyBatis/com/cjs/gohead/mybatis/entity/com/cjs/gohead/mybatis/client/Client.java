package com.cjs.gohead.mybatis.entity.com.cjs.gohead.mybatis.client;

import com.cjs.gohead.mybatis.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by chenjingshuai on 17-1-23.
 */
public class Client {

    public static void main(String args[]) throws IOException {

        Reader reader = Resources.getResourceAsReader("mybatis/masterCon.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        //Create a new student object
        Student student = new Student("Mohammad", 12, Student.Sex.male);

        //Insert student data
        session.insert("Student.insert", student);
        System.out.println("record inserted successfully");
        session.commit();
        session.close();

    }

}
