package com.example.llz.cloudbiz1.controller;

import com.example.llz.cloudbiz1.entity.Person;
import com.example.llz.cloudbiz1.iService.IMybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mybatis")
public class MybatisController {
    @Autowired
    IMybatisService mybatisService;
    
    @RequestMapping("findPersonByName")
    @ResponseBody
    public Person findPersonByName(String name){
        return mybatisService.findPersonByName(name);
    }
    
    @RequestMapping("addPerson")
    @ResponseBody
    public Integer addPerson(@RequestBody Person person){
        return mybatisService.addPerson(person);
    }
}
