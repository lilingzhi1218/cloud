package com.example.llz.cloud1.rabbitmq;

import com.example.llz.commons.rabbitmq.MqUtils;
import com.example.llz.commons.rabbitmq.config.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("rabbit")
public class RabbitMqController {
    
    @Autowired(required = false)
    MqUtils mqUtils;

    @RequestMapping("queue")
    @ResponseBody
    public void findAllCity(){
        if (mqUtils == null) return;
        mqUtils.sendToQueue(RabbitConfig.QUEUE_NAME, "queue");
    }
}
