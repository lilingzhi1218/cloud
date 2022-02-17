package com.example.llz.rulengine.dto;

import com.example.llz.rulengine.entity.DecisionInfo;
import com.example.llz.rulengine.entity.DecisionRuleinfo;
import lombok.Data;

import java.util.List;

@Data
public class DecisionRuleModel {
    private DecisionRuleinfo decisionRuleinfo;
    private List<DecisionInfo> decisionInfoList;
}
