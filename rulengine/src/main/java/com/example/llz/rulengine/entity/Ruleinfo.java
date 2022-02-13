package com.example.llz.rulengine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 * @Description 业务规则表 
 * @Author  lilingzhi
 * @Date 2021-12-31 
 */

@Data
@Entity
@Table ( name ="ruleinfo" )
public class Ruleinfo  implements Serializable {

	private static final long serialVersionUID =  1223923404800593265L;

	/**
	 * 主键
	 */
	@Id
   	@Column(name = "RID" )
	private String rid;

	/**
	 * 规则编号
	 */
   	@Column(name = "RCODE" )
	private String rcode;

	/**
	 * 规则名称
	 */
   	@Column(name = "RNAME" )
	private String rname;

	/**
	 * 规则入参
	 */
   	@Column(name = "PARAMETER" )
	private String parameter;

	/**
	 * 创建时间
	 */
   	@Column(name = "CREATE_TIME" )
	private Date createTime;

	/**
	 * 更新时间
	 */
   	@Column(name = "UPDATE_TIME" )
	private Date updateTime;

	/**
	 * 描述
	 */
   	@Column(name = "REMARK" )
	private String remark;

	/**
	 * 表达式
	 */
   	@Column(name = "EXPRESSION" )
	private String expression;

}
