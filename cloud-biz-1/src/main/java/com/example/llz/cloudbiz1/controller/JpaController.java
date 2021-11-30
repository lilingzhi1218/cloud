package com.example.llz.cloudbiz1.controller;

import com.example.llz.cloudbiz1.iService.IJpaService;
import com.example.llz.commons.rabbitmq.MqUtils;
import com.example.llz.commons.rabbitmq.config.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("jpa")
public class JpaController {
    
    @Autowired
    IJpaService iJpaService;
    
    @Autowired
    MqUtils mqUtils;
    
    @RequestMapping("findAllCity")
    @ResponseBody
    public Object findAllCity(){
        mqUtils.sendToQueue(RabbitConfig.QUEUE_NAME, "查询了全部城市");
        return iJpaService.findAllCity();
    }
}
