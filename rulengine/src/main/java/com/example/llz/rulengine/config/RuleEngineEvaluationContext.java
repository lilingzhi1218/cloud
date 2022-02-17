package com.example.llz.rulengine.config;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RuleEngineEvaluationContext extends StandardEvaluationContext {
    public RuleEngineEvaluationContext() throws NoSuchMethodException {
        this.registerFunction("ceil", Math.class.getDeclaredMethod("ceil", double.class));
        this.registerFunction("floor", Math.class.getDeclaredMethod("floor", double.class));
        this.registerFunction("round", Math.class.getDeclaredMethod("round", double.class));
        this.registerFunction("union", CollectionUtils.class.getMethod("union", Iterable.class, Iterable.class));
        this.registerFunction("remove", CollectionUtils.class.getMethod("removeAll", Collection.class, Collection.class));
        this.registerFunction("and", CollectionUtils.class.getMethod("retainAll", Collection.class, Collection.class));
        this.registerFunction("parseInt", Integer.class.getMethod("parseInt", String.class));
        this.registerFunction("parseDouble", Double.class.getMethod("parseDouble", String.class));
        this.registerFunction("curDate", RuleEngineUtils.class.getMethod("getCurDate"));
        this.registerFunction("foreach", RuleEngineUtils.class.getMethod("foreach", Set.class, String.class));
        this.registerFunction("foreach", RuleEngineUtils.class.getMethod("foreach", List.class, String.class));
        this.registerFunction("surrSingleQuo", RuleEngineUtils.class.getMethod("surrSingleQuo", String.class));
        this.registerFunction("sum", RuleEngineUtils.class.getMethod("sum", Collection.class));
        this.registerFunction("join", String.class.getMethod("join", CharSequence.class, Iterable.class));
    }
}
