package com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.user;

import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring.Autowired;
import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring.Component;

@Component
public class UserService implements UserServiceInterface{

    @Autowired
    private BeanService beanService;

    @Override
    public void test(){
        System.out.println(beanService);
    }
}
