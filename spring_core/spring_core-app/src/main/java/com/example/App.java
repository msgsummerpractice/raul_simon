package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.helloSpringExample.HelloConfig;
import com.example.annotationExample.Motorcyclist;
import com.example.annotationExample.Biker;

@Configuration
@ComponentScan(basePackages = "com.example")
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        HelloConfig obj = context.getBean(HelloConfig.class);
        System.out.println(obj.myHelloJava().display());

        Motorcyclist motorcyclist = context.getBean(Motorcyclist.class);
        motorcyclist.ride();

        Biker bike = context.getBean(Biker.class);
        bike.ride();
        
        ((ConfigurableApplicationContext)context).close();
    }
}
