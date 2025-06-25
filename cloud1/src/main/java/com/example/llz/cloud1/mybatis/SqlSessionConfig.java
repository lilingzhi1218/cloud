package com.example.llz.cloud1.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * mybatis sqlSessionFactory配置
 */
@Configuration
@MapperScan(basePackages = "com.example.llz.cloud1.mybatis"
        , sqlSessionFactoryRef = "sqlSessionFactory")
public class SqlSessionConfig {

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dataSource") DataSource dataSource) throws Exception {
        //Mybatis-plus 分页插件配置必须使用MybatisSqlSessionFactoryBean，否则分页失效，使用其才可以扫描Mybatis-plus的mapper
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        bean.setPlugins(mybatisPlusInterceptor);
//        bean.setConfigurationProperties(mybatisPlusProperties.getConfigurationProperties());
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
