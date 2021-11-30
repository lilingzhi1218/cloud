package com.example.llz.commons.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RabbitProducer {
    
    @Autowired
    AmqpTemplate amqpTemplate;
    
    public void send (){
        String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        String message =  "hello,this time is " + date;
        System.out.println("生产者1：" + message);
        this.amqpTemplate.convertAndSend("queue1", message);
    }
}
