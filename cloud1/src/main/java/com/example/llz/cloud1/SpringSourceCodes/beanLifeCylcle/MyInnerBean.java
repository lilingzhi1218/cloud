package com.example.llz.cloud1.SpringSourceCodes.beanLifeCylcle;


import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class MyInnerBean {
	public MyInnerBean() {
		System.out.println("MyInnerBean#MyInnerBean");
	}
	private String id;

	private String name;
}
