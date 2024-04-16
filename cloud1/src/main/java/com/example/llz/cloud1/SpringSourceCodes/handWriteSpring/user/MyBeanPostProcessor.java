package com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.user;

import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring.BeanPostProcessor;
import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object beanProcessBeforeInitialization(String beanName, Object object) {
        return object;
    }

    @Override
    public Object beanProcessAfterInitialization(String beanName, Object bean) {
        if ("userService".equals(beanName)){
            return Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("切面逻辑");
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }
}
