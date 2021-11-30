package com.example.llz.commons.dbio.config;

import com.example.llz.commons.dbio.utils.DbDataManagerUtils;
import com.example.llz.commons.dbio.utils.JpaDbDataProcessor;
import com.example.llz.commons.dbio.support.DbDataProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

@Configuration
public class UtilConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "db-data-util.jpa")
    public JpaDbDataUtilProperties jpaDbDataUtilProperties() {
        return new JpaDbDataUtilProperties();
    }
    @Bean
    public DbDataManagerUtils dbDataManagerUtils(List<DbDataProcessor<?>> dbDataProcessorList,
                                                 JpaDbDataUtilProperties jpaDbDataUtilProperties,
                                                 ThreadPoolTaskExecutor threadPoolTaskExecutor) throws NoSuchMethodException {

        dbDataProcessorList.add( new JpaDbDataProcessor(threadPoolTaskExecutor, jpaDbDataUtilProperties));
        return new DbDataManagerUtils(dbDataProcessorList);
    }
}
