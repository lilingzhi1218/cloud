package com.example.llz.commons.rabbitmq.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class RabbitProducerTest {

    @Autowired
    RabbitProducer rabbitProducer;

    @Test
    void send() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            rabbitProducer.send();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}