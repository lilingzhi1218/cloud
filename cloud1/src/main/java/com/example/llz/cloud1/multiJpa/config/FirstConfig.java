package com.example.llz.cloud1.multiJpa.config;

import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
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
        basePackages = "com.example.llz.cloud1.multiJpa.repository.test1"//dao所在位置
        ,entityManagerFactoryRef = "firstEntityManagerFactory"
        ,transactionManagerRef = "firstTransactionManager")
@EnableTransactionManagement
public class FirstConfig {

    @Bean(name = "firstEntityManagerFactory")
    @DependsOn("firstDataSource")
    public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(@Qualifier("firstDataSource") DataSource firstDataSource,
                                                                            @Qualifier("vendorProperties")Map<String, Object> vendorProperties,
                                                                            @Qualifier("jpaProperties") JpaProperties jpaProperties){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(firstDataSource);
        em.setJpaPropertyMap(vendorProperties);
        em.setPackagesToScan("com.example.llz.cloud1.multiJpa.model");
//        em.setPersistenceUnitName("primaryPersistenceUnit");
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaPropertyMap(jpaProperties.getProperties());
        return em;
    }

    @Bean
    public EntityManager firstEntityManager(@Qualifier("firstEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return entityManagerFactory.getObject().createEntityManager();
    }

    @Bean(name = "firstTransactionManager")
    public PlatformTransactionManager firstTransactionManager(
            @Qualifier("firstEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
