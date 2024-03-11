package com.example.llz.cloud1.basic.reflect;

public class ReflectVo {
	private String name;
	
	private String printName(String name){
		if (this.name != null) {
			name = this.name;
		}
		System.out.println(name);
		return name;
	}
	
}
