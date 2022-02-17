package com.example.llz.rulengine.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  lilingzhi
 * @Date 2022-01-07 
 */

@Data
@Entity
@Table ( name ="DECISION_RULEINFO" )
public class DecisionRuleinfo  implements Serializable {

	private static final long serialVersionUID =  6616245503595555191L;

	/**
	 * 规则主键
	 */
	@Id
   	@Column(name = "RID" ,length = 36)
	private String rid;
	/**
	 * 创建时间
	 */
   	@Column(name = "CREATE_TIME" )
	private Date createTime;

	/**
	 * 运算表达式
	 */
   	@Column(name = "EXPRESSION", length = 4000)
	private String expression;

	/**
	 * 规则参数
	 */
   	@Column(name = "PARAMETER", length = 500)
	private String parameter;
	/**
	 * 规则编码
	 */
   	@Column(name = "RCODE", length = 20)
	private String rcode;
	/**
	 * 备注
	 */
   	@Column(name = "REMARK", length = 4000)
	private String remark;
	/**
	 * 规则名称
	 */
   	@Column(name = "RNAME", length = 255)
	private String rName;

	/**
	 * 分类ID
	 */
	@Column(name = "CLASS_ID", length = 36)
	private String classId;
	/**
	 * 分类名称
	 */
	@Column(name = "CLASS_NAME", length = 255)
	private String className;


	/**
	 * 决策方式（1：断路决策，2累加决策）
	 */
	@Column(name = "TYPE", length = 2)
	private String type;


	/**
	 * 更新时间
	 */
	@Column(name = "UPDATE_TIME" )
	private Date updateTime;
	
}
