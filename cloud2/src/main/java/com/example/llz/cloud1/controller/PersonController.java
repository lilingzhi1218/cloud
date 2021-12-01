package com.example.llz.cloud1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/person")
public class PersonController {

    @RequestMapping("/getPerson")
    @ResponseBody
    public Object getPerson(){
        Map<String, Object> person = new HashMap<>();
        person.put("姓名：", "李四");
        person.put("性别：", "男");
        return person;
    }


}
