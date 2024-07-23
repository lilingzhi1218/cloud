package com.example.llz.cloud1.proxy.dynamicProxy.jdkDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BaseServiceProxy {

	private BaseService target;

	public BaseServiceProxy(BaseService target) {
		this.target = target;
	}
	
	public Object getProxy(){
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if (method.getName().equals("save")){
							System.out.println("代理执行save方法");
						}
						return method.invoke(target, args);
					}
				});
	}
}
