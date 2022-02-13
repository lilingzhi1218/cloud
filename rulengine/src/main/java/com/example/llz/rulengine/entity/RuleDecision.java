package com.example.llz.rulengine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 * @Description 规则决策表
 * @Author  lilingzhi
 * @Date 2021-12-31 
 */

@Data
@Entity
@Table ( name ="ruledecision" )
public class RuleDecision  implements Serializable {

	private static final long serialVersionUID =  7268833819422988392L;

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
	 * 决策
	 */
   	@Column(name = "DECISION" )
	private String decision;

	/**
	 * 结果
	 */
   	@Column(name = "RESULT" )
	private String result;

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

}
