package com.example.llz.cloud1.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

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
	
   	@Transient
	private String area;

}
