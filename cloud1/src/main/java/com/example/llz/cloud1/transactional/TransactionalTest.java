package com.example.llz.cloud1.transactional;

import com.example.llz.cloud1.jpa.IJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("transaction")
public class TransactionalTest {
    @Autowired
    TransactionalTest1 transactionalTest1;

    @Autowired
    IJpaService jpaService;

    @RequestMapping("a")
    @Transactional(rollbackFor = Exception.class)
    public void a(){
        jpaService.update("a", "123");
        transactionalTest1.b();
        c();
    }
    private void c(){
        jpaService.update("c", "125");
        throw new NullPointerException();
    }
    
    
}
