package com.example.llz.cloudbiz1.controller;

import com.example.llz.cloudbiz1.iService.IJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("jpa")
public class JpaController {
    
    @Autowired
    IJpaService iJpaService;
    
    @RequestMapping("findAllCity")
    @ResponseBody
    public Object findAllCity(){
        return iJpaService.findAllCity();
    }
}
