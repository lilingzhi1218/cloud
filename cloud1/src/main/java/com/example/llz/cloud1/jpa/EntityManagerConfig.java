package com.example.llz.cloud1.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.llz.cloud1.jpa"//dao所在位置
        ,entityManagerFactoryRef = "entityManagerFactory"
        ,transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class EntityManagerConfig {

    @Bean(name = "entityManagerFactory")
    @DependsOn("dataSource")
    public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(@Qualifier("dataSource") DataSource firstDataSource,
                                                                            @Qualifier("vendorProperties")Map<String, Object> vendorProperties,
                                                                            @Qualifier("jpaProperties") JpaProperties jpaProperties){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(firstDataSource);
        em.setJpaPropertyMap(vendorProperties);
        em.setPackagesToScan("com.example.llz.cloud1.entity");
//        em.setPersistenceUnitName("primaryPersistenceUnit");
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaPropertyMap(jpaProperties.getProperties());
        return em;
    }

    @Bean
    public EntityManager entityManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return entityManagerFactory.getObject().createEntityManager();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
