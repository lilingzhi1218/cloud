package com.example.llz.rulengine.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description 决策表实体
 * @Author lilingzhi
 * @Date 2022-01-05
 */

@Data
@Entity
@Table(name = "DECISIONINFO")
public class DecisionInfo implements Serializable {

    private static final long serialVersionUID = 2604102477422247489L;

    /**
     * 主键
     */
    @Id
    @Column(name = "RID", length = 36)
    private String rid;

    /**
     * 规则编号
     */
    @Column(name = "RCODE", length = 100)
    private String rcode;

    /**
     * 规则名称
     */
    @Column(name = "RNAME", length = 200)
    private String rname;

    /**
     * 决策
     */
    @Column(name = "DECISION", length = 200)
    private String decision;

    /**
     * true结果
     */
    @Column(name = "RESULT_TRUE", length = 50)
    private String resultTrue;

    /**
     * false结果
     */
    @Column(name = "RESULT_FALSE", length = 50)
    private String resultFalse;

    /**
     * 比较符
     */
    @Column(name = "OPERATOR", length = 10)
    private String operator;
    
    /**
     * 比较符
     */
    @Column(name = "SORT")
    private Integer sort;

    public BigDecimal getResultTrue() {
        return new BigDecimal(resultTrue);
    }

    public void setResultTrue(BigDecimal resultTrue) {
        this.resultTrue = resultTrue.toString();
    }

    public BigDecimal getResultFalse() {
        return new BigDecimal(resultFalse);
    }

    public void setResultFalse(BigDecimal resultFalse) {
        this.resultFalse = resultFalse.toString();
    }
}
