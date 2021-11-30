package com.example.llz.commons.rabbitmq.config;

import com.example.llz.commons.rabbitmq.MqUtils;
import com.example.llz.commons.rabbitmq.consumer.RabbitConsumer1;
import com.example.llz.commons.rabbitmq.consumer.RabbitConsumer2;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public final static String QUEUE_NAME = "queue";
    @Bean
    public Queue initQueue(){ 
        System.out.println("创建队列");
        return new Queue(QUEUE_NAME);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RabbitConsumer1 rabbitConsumer(){
        return new RabbitConsumer1();
    }
    @Bean
    @ConditionalOnMissingBean
    public RabbitConsumer2 rabbitConsumer2(){
        return new RabbitConsumer2();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public MqUtils mqUtils(){
        return new MqUtils();
    }
    
}
