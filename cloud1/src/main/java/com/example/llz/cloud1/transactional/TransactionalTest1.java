package com.example.llz.cloud1.transactional;

import com.example.llz.cloud1.jpa.IJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalTest1 {
    @Autowired
    IJpaService jpaService;

    public void b(){
        innerB();
    }
    private void innerB(){
        jpaService.update("b", "124");
    }
}
