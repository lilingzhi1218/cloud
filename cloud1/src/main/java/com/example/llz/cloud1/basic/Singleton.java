package com.example.llz.cloud1.basic;

public class Singleton {
	private Singleton(){}
	private static volatile Singleton singleton = null;  //不建立对象
	public static Singleton getInstance(){
		if(singleton == null) {        //先判断是否为空
			synchronized (Singleton.class){//为空才进入同步代码块
				if (singleton == null){ //双重监测机制
					singleton = new Singleton ();  //懒汉式做法 
				}
			}
		}
		return singleton;
	}
}
