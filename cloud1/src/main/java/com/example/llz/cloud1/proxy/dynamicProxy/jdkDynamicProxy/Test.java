package com.example.llz.cloud1.proxy.dynamicProxy.jdkDynamicProxy;

public class Test {
	public static void main(String[] args) {
		BaseService baseService = new BaseService();
		BaseServiceProxy baseServiceProxy = new BaseServiceProxy(baseService);
		BaseService baseService1 = (BaseService) baseServiceProxy.getProxy();
		baseService1.save();
	}
}
