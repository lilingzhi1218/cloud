package com.example.llz.cloud1.thread.threadPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("threadPool")
public class SpringBootTreadPoolTest {
    @Autowired
    ThreadPoolTaskExecutor executor;
    
    @RequestMapping("test")
    public void test(){
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executor.execute(()->{
                System.out.println(Thread.currentThread().toString() + finalI);
            });
        }
    }
}
