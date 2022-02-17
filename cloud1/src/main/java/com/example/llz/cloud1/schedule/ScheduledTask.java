package com.example.llz.cloud1.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@Async
public class ScheduledTask {
    Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
//    @Scheduled(cron = "0/5 * * * * *")
//    public void schedule1(){
//        logger.info("任务1正在线程：{}上处理", Thread.currentThread().getName());
//    }
//    @Scheduled(cron = "0/5 * * * * *")
//    public void schedule2(){
//        logger.info("任务2正在线程：{}上处理", Thread.currentThread().getName());
//    }
//    @Scheduled(cron = "0/5 * * * * *")
//    public void schedule3(){
//        logger.info("任务3正在线程：{}上处理", Thread.currentThread().getName());
//    }
    //TODO 支持动态暂停、开始定时任务
}
