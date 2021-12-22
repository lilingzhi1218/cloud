package com.example.llz.cloud2.form.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 * @Description  
 * @Author  lilingzhi
 * @Date 2021-12-14 
 */

@Data
@Entity
@Table ( name ="forminfo" )
public class Forminfo  implements Serializable {

	private static final long serialVersionUID =  8877787094970378399L;
	@Id
   	@Column(name = "RID" )
	private String rid;

   	@Column(name = "BORELATEID" )
	private String borelateid;

   	@Column(name = "COLUMNS" )
	private String columns;

   	@Column(name = "CONFIG" )
	private String config;

   	@Column(name = "CREATEID" )
	private String createid;

   	@Column(name = "DEVICE" )
	private String device;

   	@Column(name = "EXCHANGEID" )
	private String exchangeid;

   	@Column(name = "FORMCLASS" )
	private String formclass;

   	@Column(name = "FORMCLASSNAME" )
	private String formclassname;

   	@Column(name = "FORMRULES" )
	private String formrules;

   	@Column(name = "FORMSTYLE" )
	private String formstyle;

   	@Column(name = "FORMTYPE" )
	private Double formtype;

   	@Column(name = "FORMPATH" )
	private String formpath;

   	@Column(name = "GLOBALEXPRESSION" )
	private String globalexpression;

   	@Column(name = "LIST_DM" )
	private String listDm;

   	@Column(name = "MANAGER" )
	private String manager;

   	@Column(name = "MDATE" )
	private Date mdate;

   	@Column(name = "TEMPLATEPATH" )
	private String templatepath;

   	@Column(name = "TITLE" )
	private String title;

   	@Column(name = "VERNUM" )
	private Double vernum;

   	@Column(name = "FRONTOTHERMSG" )
	private String frontothermsg;

   	@Column(name = "BOPRIMARYKEY" )
	private String boprimarykey;

   	@Column(name = "ALIASNAME" )
	private String aliasname;

   	@Column(name = "PUBLISHSTATUS" )
	private String publishstatus;

   	@Column(name = "EDITION" )
	private String edition;

   	@Column(name = "LAZYLOADEXPRESSION" )
	private Double lazyloadexpression;

}
