package com.example.llz.rulengine.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description  
 * @Author  lilingzhi
 * @Date 2022-01-10 
 */

@Data
@Entity
@Table ( name ="DECISION_RULECLASS" )
public class DecisionRuleClass  implements Serializable {

	private static final long serialVersionUID =  91999426539139925L;

	/**
	 * 分类主键
	 */
	@Id
   	@Column(name = "RID", length = 36)
	private String rid;

	/**
	 * 分类名称
	 */
   	@Column(name = "CLASS_NAME", length = 255)
	private String className;

	/**
	 * 父类ID
	 */
   	@Column(name = "PRID", length = 36)
	private String pRid;

    /**
     * 分类名称
     */
    @Column(name = "PNAME", length = 255)
    private String pName;


    /**
	 * 排序
	 */
   	@Column(name = "SORT" )
	private Long sort;

}
