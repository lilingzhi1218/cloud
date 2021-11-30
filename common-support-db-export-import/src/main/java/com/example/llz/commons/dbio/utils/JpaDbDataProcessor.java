package com.example.llz.commons.dbio.utils;



import com.example.llz.commons.dbio.config.JpaDbDataUtilProperties;
import com.example.llz.commons.dbio.support.DbDataProcessor;
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
