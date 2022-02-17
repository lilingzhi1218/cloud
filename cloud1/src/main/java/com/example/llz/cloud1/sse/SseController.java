package com.example.llz.cloud1.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.Emitter;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
@RequestMapping("sse")
public class SseController {
    @Autowired
    private TaskExecutor taskExecutor;
    @RequestMapping("standardSse")
    public SseEmitter standardSse(){
        SseEmitter sseEmitter = new SseEmitter();
        taskExecutor.execute(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    try {
                        Map<String, Date> data = Collections.singletonMap(String.valueOf(i), new Date());
                        sseEmitter.send(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                sseEmitter.complete();
            }
        });
        return sseEmitter;
    }
}
