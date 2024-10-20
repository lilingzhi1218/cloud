package com.example.llz.cloud1.multiJpa.config;

import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring.Autowired;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.llz.cloud1.multiJpa.repository.test2",
        entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager"
)
public class SecondConfig {

    @Autowired
    @Qualifier("secondDataSource")
    private DataSource secondDataSource;

    @Autowired
    @Qualifier("vendorProperties")
    private Map<String, Object> vendorProperties;

    @Bean(name = "secondEntityManager")
    public LocalContainerEntityManagerFactoryBean secondEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondDataSource);
        em.setJpaPropertyMap(vendorProperties);
        em.setPackagesToScan("com.example.llz.cloud1.multiJpa.repository.test2");
//        em.setPersistenceUnitName("primaryPersistenceUnit");
        return em;
    }

    @Bean(name = "secondEntityManager")
    public EntityManager entityManager(@Qualifier("secondEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return entityManagerFactory.getObject().createEntityManager();
    }

    @Bean(name = "secondTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("secondEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
