package com.example.llz.commons.utils;



import com.example.llz.commons.config.JpaDbDataUtilProperties;
import com.example.llz.commons.support.DbDataProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

public class JpaDbDataProcessor implements DbDataProcessor<Map<Integer, String>> {
    
    private final ThreadPoolTaskExecutor taskExecutor;
    private final JpaDbDataUtilProperties jpaDbDataUtilProperties;

    public JpaDbDataProcessor(ThreadPoolTaskExecutor taskExecutor,
                              JpaDbDataUtilProperties jpaDbDataUtilProperties) throws NoSuchMethodException {
        this.taskExecutor = taskExecutor;
        this.jpaDbDataUtilProperties = jpaDbDataUtilProperties;
    }

}
