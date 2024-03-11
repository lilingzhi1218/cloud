package com.example.llz.cloud1.basic.reflect;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TryIt {
	
	public static void main(String[] args) {
		//反射:jvm为每个类生成一个class对象放到堆中
		
		//三种方式反射
		Class<ReflectVo> clazz = ReflectVo.class;
		try {
			clazz = (Class<ReflectVo>) Class.forName("com.example.llz.cloud1.basic.reflect.ReflectVo");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		clazz = (Class<ReflectVo>) new ReflectVo().getClass();
		
		//通过无参构造创建对象
		ReflectVo reflectVo = null;
		try {
			reflectVo = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		try {
			//getDeclareField是本类中所声明的字段，getField是本类及其父类中的public字段
			Field field = clazz.getDeclaredField("name");
			//设置访问权限
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}

		try {
			Method method = clazz.getDeclaredMethod("printName", String.class);
			method.setAccessible(true);
			Object invoke = method.invoke(reflectVo, "reflect");
			
			assert invoke.equals("reflect");
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
}
