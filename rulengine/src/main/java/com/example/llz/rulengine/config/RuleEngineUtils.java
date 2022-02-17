package com.example.llz.rulengine.config;

import com.example.llz.rulengine.utils.DateUtil;
import com.example.llz.rulengine.utils.assertion.ExceptionAssertEnum;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RuleEngineUtils {

    public static String getCurDate(){
        Date date = new Date();
        return DateUtil.dateToString(date);
    }
    
    public static List<Object> foreach(List<Object> list, String opt) throws NoSuchMethodException {
        SpelExpressionParser parser = new SpelExpressionParser();
        RuleEngineEvaluationContext context = new RuleEngineEvaluationContext();
        return list.stream().map(o -> {
            context.setVariable("it", o);
            return parser.parseExpression(opt).getValue(context);
        }).collect(Collectors.toList());
    }    
    public static Set<Object> foreach(Set<Object> set, String opt) throws NoSuchMethodException {
        SpelExpressionParser parser = new SpelExpressionParser();
        RuleEngineEvaluationContext context = new RuleEngineEvaluationContext();
        return set.stream().map(o -> {
            context.setVariable("it", o);
            return parser.parseExpression(opt).getValue(context);
        }).collect(Collectors.toSet());
    }
    
    public static String surrSingleQuo(String str){
        return "'".concat(str).concat("'");
    }
    
    public static BigDecimal sum(Collection collection){
        BigDecimal sum = new BigDecimal(0);
        for (Object o : collection) {
            try {
                sum = sum.add(new BigDecimal(o.toString()));
            }catch (Exception e){
                sum = sum.add(new BigDecimal(0));
            }
        }
        return sum;
    }

    public static String getDate(String sDate){
        String[] sa = {
                "yyyy-MM-dd HH:mm:ss",
                "yyyyMMdd HH:mm:ss",
                "yyyy.MM.dd HH:mm:ss",
                "yyyy/MM/dd HH:mm:ss",
                "yyyy年MM月dd日 HH时mm分ss秒"
        };

        for(String s : sa) {
            Date d = null;
            try {
                d = getDate(s, sDate);
            } catch (ParseException e) {
                continue;
            }
            if(d != null) {
                return new SimpleDateFormat(sa[0]).format(d);
            }
        }
        ExceptionAssertEnum.ServiceError.throwIsTrue(true, "日期格式有误");
        return null;
    }
    public static Date getDate(String sFormat, String sDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
        sdf.setLenient(false);
        return sdf.parse(sDate);
    }
    
}
