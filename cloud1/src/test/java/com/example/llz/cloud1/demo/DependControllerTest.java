package com.example.llz.cloud1.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DependControllerTest {

    @Autowired
    DependController dependController;
    @Test
    public void testListDbUtilProperties(){
        dependController.listDbUtilProperties();
    }
}