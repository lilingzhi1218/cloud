package com.example.llz.cloudbiz1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import lombok.Data;

/**
 * @Description  
 * @Author  lilingzhi
 * @Date 2021-11-12 
 */

@Data
@Entity
@Table ( name ="CITY" )
public class City  implements Serializable {

	private static final long serialVersionUID =  2166143543924081823L;

   	@Column(name = "NAME" )
	private String name;

	@Id
	@Column(name = "CITY_ID" )
	private String cityId;

   	@Column(name = "PROVINCE" )
	private String province;

}
