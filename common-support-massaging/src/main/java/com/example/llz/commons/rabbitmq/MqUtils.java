package com.example.llz.commons.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class MqUtils {

    @Autowired
    AmqpTemplate amqpTemplate;
    
    public void sendToQueue(String queueName, String message){
      amqpTemplate.convertAndSend(queueName, message);
    }
    
}
