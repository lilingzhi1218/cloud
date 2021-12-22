package com.example.llz.cloud2.form.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import lombok.Data;

/**
 * @Description  
 * @Author  lilingzhi
 * @Date 2021-12-14 
 */

@Data
@Entity
@Table ( name ="formclass" )
public class Formclass  implements Serializable {

	private static final long serialVersionUID =  3149395213735547011L;
	@Id
   	@Column(name = "CODE" )
	private String code;

   	@Column(name = "NAME" )
	private String name;

   	@Column(name = "PCODE" )
	private String pcode;

}
