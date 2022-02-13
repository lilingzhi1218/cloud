package com.example.llz.rulengine.controller;

import com.example.llz.rulengine.dao.RuleDao;
import com.example.llz.rulengine.entity.Ruleinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.ScriptException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("rulengine")
public class RulengineController {
    @Autowired
    private RuleDao ruleDao;
    @RequestMapping("saveOrUpdateRule")
    @ResponseBody
    public void saveOrUpdateRule(@RequestBody Ruleinfo ruleinfo) throws Exception {
        if (ruleinfo == null){
            throw new Exception("规则为空");
        }
        Ruleinfo oldRule = ruleDao.findByRcode(ruleinfo.getRcode());
        if (oldRule != null){
            ruleinfo.setRid(oldRule.getRid());
            ruleinfo.setCreateTime(oldRule.getCreateTime());
        }else {
            ruleinfo.setRid(UUID.randomUUID().toString());
            ruleinfo.setCreateTime(new Date());
        }
        ruleinfo.setUpdateTime(new Date());
        ruleDao.save(ruleinfo);
    }

    @RequestMapping("counting")
    @ResponseBody
    public Object counting(String code, Map<String, Object> params) throws ScriptException, NoSuchMethodException {
        Ruleinfo rule = ruleDao.findByRcode(code);
        String parameter = rule.getParameter();
//        ScriptEngineManager scriptManager = new ScriptEngineManager();
//        ScriptEngine engine = scriptManager.getEngineByName("JavaScript");
//        Object eval = engine.eval(rule.getExpression());
//        Invocable invocable = (Invocable) engine;
//        return invocable.invokeFunction("greet");
        String expression = rule.getExpression();
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expression);
        return exp.getValue(String.class);
    }
}
