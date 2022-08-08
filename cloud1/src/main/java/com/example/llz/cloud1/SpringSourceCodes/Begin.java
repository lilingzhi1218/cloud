package com.example.llz.cloud1.SpringSourceCodes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Begin {
    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
    }
}
