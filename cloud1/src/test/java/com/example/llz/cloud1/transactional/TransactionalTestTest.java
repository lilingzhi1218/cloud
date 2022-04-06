package com.example.llz.cloud1.transactional;

import com.example.llz.cloud1.jpa.IJpaService;
import com.example.llz.cloud1.jpa.JpaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionalTestTest {
    @Autowired
    TransactionalTest transactionalTest;

    @Autowired
    IJpaService jpaService;
    
    @Test
    public void testA(){
        transactionalTest.a();
    }
    
    
}
