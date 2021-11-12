package com.example.llz.cloudbiz1.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @Description  
 * @Author  lilingzhi
 * @Date 2021-11-12 
 */

@Data
public class RelPersonCity  implements Serializable {

	private static final long serialVersionUID =  3032889838167999875L;
	private String rel_Id;
	private String person_Id;
	private String city_Id;

}
