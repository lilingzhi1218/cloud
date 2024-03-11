package com.example.llz.cloud1.jpa;


import com.example.llz.commons.rabbitmq.MqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return iJpaService.findAllCity();
    }
    @RequestMapping("updateCity")
    @ResponseBody
    public void updateCity(String name, String id){
        iJpaService.update(name, id);
    }
    
}
