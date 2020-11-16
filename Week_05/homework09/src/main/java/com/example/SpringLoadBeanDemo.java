package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringLoadBeanDemo {
    private final static String BEAN_PATH = "spring-bean.xml";


    public static void main(String[] args) {
//        Student student = loadBeanByXml( Student.class);
//        Student student = loadBeanByAnnotation(Student.class);
        Student student = loadBeanByDefinition(Student.class);
        System.out.println(student.toString());
    }

    private static <T> T loadBeanByDefinition(Class<T> clzz) {
        return null;
    }

    private static <T> T loadBeanByXml(Class<T> clzz) {
        return new ClassPathXmlApplicationContext(BEAN_PATH).getBean(clzz.getSimpleName().toLowerCase(), clzz);
    }

    private static <T> T loadBeanByAnnotation(Class<T> clzz) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(clzz);
        context.refresh();
        try {
            return context.getBean(clzz.getSimpleName().toLowerCase(),clzz);
        }finally {
            context.close();
        }
    }


}