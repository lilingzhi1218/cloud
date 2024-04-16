package com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring;

public interface BeanPostProcessor {
    Object beanProcessBeforeInitialization(String beanName, Object object);
    Object beanProcessAfterInitialization(String beanName, Object object);

}
