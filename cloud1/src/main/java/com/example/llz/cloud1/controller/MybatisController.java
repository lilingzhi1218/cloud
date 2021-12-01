package com.example.llz.cloud1.controller;

import com.example.llz.cloud1.Annotation.LogAspect;
import com.example.llz.cloud1.entity.Person;
import com.example.llz.cloud1.entity.RelPersonCity;
import com.example.llz.cloud1.iService.IMybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("mybatis")
public class MybatisController {
    @Autowired
    IMybatisService mybatisService;
    @LogAspect
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

    @LogAspect
    @RequestMapping("findAllPerson")
    @ResponseBody
    public List<Person> findAllPerson(){
        return mybatisService.findAllPerson();
    }
    
    @RequestMapping("findCityRelById")
    @ResponseBody
    public RelPersonCity findCityRelById(String id){
        return mybatisService.findCityRelById(id);
    }
}
