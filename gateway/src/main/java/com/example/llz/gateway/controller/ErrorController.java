package com.example.llz.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ErrorController {

    @RequestMapping("/fallback")
    public Object fallback(){
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "服务器繁忙");
        result.put("state", false);
        return result;
    }
}
