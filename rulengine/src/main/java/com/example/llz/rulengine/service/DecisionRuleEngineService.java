package com.example.llz.rulengine.service;

import com.google.common.collect.Maps;
import com.southgis.ibase.rule.RuleEngineEvaluationContext;
import com.southgis.ibase.rule.RuleEngineUtils;
import com.southgis.ibase.rule.dao.DecisionInfoDao;
import com.southgis.ibase.rule.dao.DecisionRuleinfoDao;
import com.southgis.ibase.rule.decisionrule.service.IDecisionRuleEngineService;
import com.southgis.ibase.rule.dicisionrule.dto.RuleData;
import com.southgis.ibase.rule.dicisionrule.entity.DecisionInfo;
import com.southgis.ibase.rule.dicisionrule.entity.DecisionRuleinfo;
import com.southgis.ibase.rule.enums.ParamTypeEnum;
import com.southgis.ibase.utils.CheckUtil;
import com.southgis.ibase.utils.DateUtil;
import com.southgis.ibase.utils.assertion.ExceptionAssertEnum;
import com.southgis.ibase.utils.json.JsonUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 决策规则引擎服务
 */
@Service
public class DecisionRuleEngineService implements IDecisionRuleEngineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionRuleEngineService.class);

    @Autowired
    private DecisionRuleinfoDao decisionRuleinfoDao;
    @Autowired
    private DecisionInfoDao decisionInfoDao;


    @Override
    public List<Map<String, Object>> executeRules(List<RuleData> ruleDataList) {
        List<Map<String, Object>> resultMapList = new ArrayList<>();
        for (RuleData ruleData : ruleDataList) {
            Map<String, Object> resultMap = this.executeRule(ruleData);
            resultMap.put("code", ruleData.getCode());
            resultMapList.add(resultMap);
        }
        return resultMapList;
    }


    @SneakyThrows
    @Override
    public Map<String, Object> executeRule(RuleData ruleData) {
        String code = ruleData.getCode();
        DecisionRuleinfo ruleinfo = decisionRuleinfoDao.findByRcode(code);
        ExceptionAssertEnum.ServiceError.throwIsNull(ruleinfo, "找不到代码为" + code + "的业务规则配置");
        return executeRule(ruleinfo, ruleData.getArgs());
    }

    @Override
    public Map<String, Object> executeRule(DecisionRuleinfo ruleinfo, Map<String, Object> args) throws NoSuchMethodException {
        ExceptionAssertEnum.ServiceError.throwIsNull(ruleinfo, "业务规则配置对象为空");
        String paramsStr = ruleinfo.getParameter();
        //校验参数
        checkParams(paramsStr, args);
        //获取条件运算结果value
        String expression = ruleinfo.getExpression();
        ExpressionParser parser = new SpelExpressionParser();
        RuleEngineEvaluationContext context = new RuleEngineEvaluationContext();
        Object value = analyzeExpr(expression, args, context, parser);
        ExceptionAssertEnum.ServiceError.throwIsNull(value, "运算结果为空");
        //解析结果
        LOGGER.info("【{}】决策规则运算结果为:{}", ruleinfo.getRName(), value);
        return analyzeResult(ruleinfo, parser, value);
    }

    /**
     * 检查参数
     *
     * @param paramsStr 形参
     * @param args      实参
     */
    private void checkParams(String paramsStr, Map<String, Object> args) {
        if (CheckUtil.isNullorEmpty(paramsStr)) {
            return;
        }
        ExceptionAssertEnum.ServiceError.throwIsNull(args, "形参和实参数量不一致");
        Map<String, Class<?>> paramsMap = paramsStrToMap(paramsStr);
        paramsMap.forEach((k, v) -> {
            Object arg = args.get(k);
            ExceptionAssertEnum.ServiceError.throwIsNull(arg, k + "参数为空");
            //class1.isAssignableFrom(class2),class2是否为class1的相同类、子类或者子接口
            if (v.isAssignableFrom(Date.class)) {
                try {
                    args.put(k, handleDateType(arg));
                } catch (Exception e) {
                    e.printStackTrace();
                    ExceptionAssertEnum.ServiceError.throwIsTrue(
                            true, String.format("【%s】参数日期格式有误【%s】", k, arg));
                }
                return;//continue
            }
            ExceptionAssertEnum.ServiceError.throwIsTrue(
                    !v.isAssignableFrom(arg.getClass()), String.format("【%s】参数类型不对", k));
        });
    }

    /**
     * 日期格式参数可以是字符串、时间戳、或者是date格式
     * @param arg 日期参数
     * @return yyyy-MM-dd HH:mm:ss
     */
    private Object handleDateType(Object arg) {
        if (arg instanceof Date) {
            return DateUtil.dateToString((Date) arg);
        }
        if (arg instanceof String) {
            return RuleEngineUtils.getDate((String) arg);
        }
        return DateUtil.dateToString(new Date((Long) arg));
    }

    /**
     * 形参字符串转map
     *
     * @param paramsStr 形参字符串
     * @return map<参数名, 参数类型>
     */
    private Map<String, Class<?>> paramsStrToMap(String paramsStr) {
        ArrayList<HashMap<String, Object>> paramList = JsonUtil.jsonStringToList(paramsStr);
        Map<String, Class<?>> paramsMap = new HashMap<>();
        for (HashMap<String, Object> map : paramList) {
            paramsMap.put((String) map.get("pname"), ParamTypeEnum.getClazz((String) map.get("ptype")));
        }
        return paramsMap;
    }

    /**
     * 解析表达式
     *
     * @param expr    表达式字符串
     * @param args    参数
     * @param context 上下文环境
     * @param parser  el解析器
     * @return 运算结果
     */
    private Object analyzeExpr(String expr, Map<String, Object> args, StandardEvaluationContext context, ExpressionParser parser) {
        Object value;
        if (CheckUtil.isNullorEmpty(expr)) {
            value = args;
        } else {
            context.setVariables(args);
            Expression exp = parser.parseExpression(expr);
            value = exp.getValue(context);
        }
        return value;
    }

    /**
     * 解析运算结果
     *
     * @param decisionRuleinfo 规则代码
     * @param parser           spel解析器
     * @param value            运算结果
     * @return 解析结果
     */
    private Map<String, Object> analyzeResult(DecisionRuleinfo decisionRuleinfo, ExpressionParser parser, Object value) throws NoSuchMethodException {
        ExceptionAssertEnum.ServiceError.throwIsNull(value, "运算结果为空");
        List<DecisionInfo> decisionList = decisionInfoDao.findAllByRcode(decisionRuleinfo.getRcode());
        RuleEngineEvaluationContext context = new RuleEngineEvaluationContext();
        //是否为断路决策
        boolean isBreak = "1".equals(decisionRuleinfo.getType());
        context.setVariable("result", value);
        BigDecimal sum = new BigDecimal(0);
        for (DecisionInfo dec : decisionList) {
            String compareExpr;
            if (dec.getOperator().equals("contains")) {
                context.setVariable("decision", dec.getDecision());
                compareExpr = "#result.contains(#decision)";
            } else {
                context.setVariable("decision", new BigDecimal(dec.getDecision()));
                compareExpr = "#result " + dec.getOperator() + " #decision ";
            }
            Boolean paired = parser.parseExpression(compareExpr).getValue(context, boolean.class);
            paired = Optional.ofNullable(paired).orElse(false);
            sum = sum.add(paired ? dec.getResultTrue() : dec.getResultFalse());
            if (paired && isBreak) break;
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("result", sum);
        return resultMap;
    }

}
