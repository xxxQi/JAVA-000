package com.example.starter.config;

import com.example.starter.model.Klass;
import com.example.starter.model.School;
import com.example.starter.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AutoConfiguration {
    @Autowired
    private Student student100;

    @Autowired
    private Student student123;

    @Bean
    public Student student100() {
        Student student = new Student();
        student.setId(100);
        student.setName("xxxQi");
        return student;
    }

    @Bean
    public Student student123() {
        Student student = new Student();
        student.setId(123);
        student.setName("Reuben");
        return student;
    }

    @Bean
    public Klass class1() {
        Klass klass = new Klass();
        List<Student> list = new ArrayList<>();
        list.add(student100);
        list.add(student123);
        klass.setStudents(list);
        return klass;
    }

    @Bean
    public School school() {
        return new School();
    }

}