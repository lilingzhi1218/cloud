package com.example.llz.rulengine.controller;


import com.example.llz.rulengine.config.RuleEngineEvaluationContext;
import com.example.llz.rulengine.dto.RuleData;
import com.example.llz.rulengine.iService.IDecisionRuleEngineService;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("decisionRuleEngine")
public class DecisionRuleEngineController {
    @Autowired
    private IDecisionRuleEngineService decisionRuleEngineService;

    /**
     * 规则计算
     * @param ruleData {"code":"xxx", "param":{key:value}}
     * @return {"code": "xxx", "result": number}
     */
    @RequestMapping("executeRule")
    @ResponseBody
    Object executeRule(@RequestBody RuleData ruleData){
        ExceptionAssertEnum.ArgumentNull.throwIsNull(ruleData, "参数为空");
        return decisionRuleEngineService.executeRule(ruleData);
    }
    /**
     * 多条规则计算
     * @param ruleDataList [{"code":"xxx", "param":{key:value}}]
     * @return [{"code": "xxx", "result": number}]
     */
    @RequestMapping("executeRules")
    @ResponseBody
    Object executeRules(@RequestBody List<RuleData> ruleDataList){
        ExceptionAssertEnum.ArgumentNull.throwIsNull(ruleDataList, "参数为空");
        return decisionRuleEngineService.executeRules(ruleDataList);
    }

    /**
     * 测试表达式代码
     * @param expStr 表达式字符串
     */
    @RequestMapping("run")
    @ResponseBody
    public Object run(String expStr, String args) throws Exception {
        ExpressionParser parser = new SpelExpressionParser();
        RuleEngineEvaluationContext con = new RuleEngineEvaluationContext();
        Expression exp = parser.parseExpression(expStr);
        if (args != null && !args.isEmpty()) {
            con.setVariables(.jsonStringToMap(args));
        }
        return exp.getValue(con);
    }
}
