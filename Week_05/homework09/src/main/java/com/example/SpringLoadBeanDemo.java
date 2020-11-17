package com.example;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringLoadBeanDemo {
    private final static String BEAN_PATH = "classpath:spring-bean.xml";

    public static void main(String[] args) {
//        Student student = loadBeanByAnnotation(Student.class);
//        Student student = loadBeanByXml( Student.class);
//        Student student = loadBeanByFile(Student.class);
        Student student = loadBeanByDefinition(Student.class);
        System.out.println(student.toString());
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

    private static <T> T loadBeanByXml(Class<T> clzz) {
        return new ClassPathXmlApplicationContext(BEAN_PATH).getBean(clzz.getSimpleName().toLowerCase(), clzz);
    }

    private static <T> T loadBeanByFile(Class<T> clzz) {
        return new FileSystemXmlApplicationContext(BEAN_PATH).getBean(clzz.getSimpleName().toLowerCase(),clzz);
    }

    private static <T> T loadBeanByDefinition(Class<T> clzz) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        RootBeanDefinition beanDefinition = new RootBeanDefinition(clzz);
        beanFactory.registerBeanDefinition(clzz.getSimpleName().toLowerCase(),beanDefinition);
        return beanFactory.getBean(clzz.getSimpleName().toLowerCase(),clzz);
    }

}