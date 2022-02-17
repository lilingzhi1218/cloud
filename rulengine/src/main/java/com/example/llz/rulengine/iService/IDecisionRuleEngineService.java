package com.example.llz.rulengine.iService;

import com.example.llz.rulengine.dto.RuleData;
import com.example.llz.rulengine.entity.DecisionRuleinfo;

import java.util.List;
import java.util.Map;

public interface IDecisionRuleEngineService {
    /**
     * 规则计算
     * @param ruleData {"code":"xxx", "param":{key:value}}
     * @return {"result": number}
     */
    Map<String, Object> executeRule(RuleData ruleData);
    /**
     * 多条规则计算
     * @param ruleDataList [{"code":"xxx", "param":{key:value}}]
     * @return [{"code": "xxx", "result": number}]
     */
    List<Map<String, Object>> executeRules(List<RuleData> ruleDataList);
    /**
     * 规则计算
     * @param ruleinfo 业务规则配置
     * @param args 参数
     * @return
     * @throws Exception
     */
    Object executeRule(DecisionRuleinfo ruleinfo, Map<String, Object> args) throws Exception;
}
