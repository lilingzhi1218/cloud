package com.example.llz.cloudbiz1.controller;

import com.example.llz.cloudbiz1.entity.Person;
import com.example.llz.cloudbiz1.iService.IMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mongo")
public class MongoDbController {
    @Autowired
    IMongoDbService iMongoDbService;
    
    @RequestMapping("insert")
    @ResponseBody
    public Person insert(@RequestBody Person person){
        return iMongoDbService.insert(person);
    }
    @RequestMapping("findById")
    @ResponseBody
    public Person findById(String id){
        return iMongoDbService.findById(id);
    }

    @RequestMapping("delById")
    @ResponseBody
    public long delById(String id){
        return iMongoDbService.delById(id);
    }
}
