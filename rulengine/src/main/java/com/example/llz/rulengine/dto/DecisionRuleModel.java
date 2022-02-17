package com.example.llz.rulengine.dto;

import com.southgis.ibase.rule.dicisionrule.entity.DecisionInfo;
import com.southgis.ibase.rule.dicisionrule.entity.DecisionRuleinfo;
import lombok.Data;

import java.util.List;

@Data
public class DecisionRuleModel {
    private DecisionRuleinfo decisionRuleinfo;
    private List<DecisionInfo> decisionInfoList;
}
