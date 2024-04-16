package com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.user;

import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring.MySpringApplicationContext;
import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.user.ApplicationConfig;

public class Test {
    public static void main(String[] args) throws Exception {
        MySpringApplicationContext mySpringApplicationContext = new MySpringApplicationContext(ApplicationConfig.class);

        UserServiceInterface userService = (UserServiceInterface) mySpringApplicationContext.getBean("userService");
        userService.test();
    }
}
