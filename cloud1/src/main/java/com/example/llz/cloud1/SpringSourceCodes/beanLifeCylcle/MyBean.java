package com.example.llz.cloud1.SpringSourceCodes.beanLifeCylcle;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name = "my_bean")
@Data
@Component
public class MyBean implements Serializable, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, BeanPostProcessor {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;
	
	@Autowired
	@Transient
	private MyInnerBean myInnerBean;

	public MyBean() {
		System.out.println("MyBean#MyBean");
	}

	@Override
	public void setBeanName(String s) {
		System.out.println("BeanNameAware#setBeanName");
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("BeanClassLoaderAware#setBeanClassLoader");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactoryAware#setBeanFactory");
	}


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("BeanPostProcessor#postProcessBeforeInitialization-" + beanName);
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("BeanPostProcessor#postProcessAfterInitialization-" + beanName);
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

}
