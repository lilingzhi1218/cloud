package com.example.llz.commons.rabbitmq.consumer;

import com.example.llz.commons.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_NAME)
public class RabbitConsumer1 {
    
    @RabbitHandler
    public void process(String message){
        System.out.println("消费者1：收到了“" + message + "”的信息，马上执行任务1");
    }
}
