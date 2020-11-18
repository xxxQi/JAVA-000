package com.example.starter;

import com.example.starter.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStarterApplication.class,args);
    }
    @Autowired
    private School school;


    @PostConstruct
    public void init(){
        school.ding();
        school.getClass1().dong();
    }
}