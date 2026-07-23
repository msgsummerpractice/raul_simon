package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Configuration
@ComponentScan(basePackages = "com.example")
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(HelloConfig.class);
        HelloConfig obj = context.getBean(HelloConfig.class);
        System.out.println(obj.myHelloJava().display());
        ((ConfigurableApplicationContext)context).close();
    }
}
