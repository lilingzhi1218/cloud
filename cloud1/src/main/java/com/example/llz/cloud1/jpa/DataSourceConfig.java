package com.example.llz.cloud1.jpa;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties(){
        return new JpaProperties();
    }

    @Bean(name = "hibernateProperties")
    @ConfigurationProperties(prefix = "spring.jpa.hibernate")
    @Primary
    public HibernateProperties defaultJpaHibernate()
    {
        return new HibernateProperties();
    }

    @Bean(name = "vendorProperties")
    public Map<String, Object> getVendorProperties(JpaProperties jpaProperties,
                                                   HibernateProperties hibernateProperties){
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

}
