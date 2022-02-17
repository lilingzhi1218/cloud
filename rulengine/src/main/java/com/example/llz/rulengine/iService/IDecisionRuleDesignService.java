package com.example.llz.rulengine.iService;


import com.example.llz.commons.utils.pageData.PageQueryData;
import com.example.llz.commons.utils.treeEntity.TreeEntity;
import com.example.llz.rulengine.dto.DecisionRuleModel;
import com.example.llz.rulengine.entity.DecisionRuleClass;
import com.example.llz.rulengine.entity.DecisionRuleinfo;

import java.util.List;
import java.util.Map;


public interface IDecisionRuleDesignService {

     /**
      * 保存规则
      * @param ruleinfo 规则模型
      */
     void saveOrUpdateRule(DecisionRuleModel ruleinfo);

     /**
      * 规则列表
      * @param classId 分类id
      */
     void listDecisionRules(PageQueryData<DecisionRuleinfo> pageQueryData, String classId);

     /**
      * 获取规则详情
      * @param ruleId 规则id
      */
     DecisionRuleModel getDecisionRule(String ruleId);

     /**
      * 删除规则
      * @param ruleIds 规则ids
      */
     void delDecisionRule(List<String> ruleIds);

     /**
      * 保存分类
      * @param ruleClass 分类实体
      */
     String saveClass(DecisionRuleClass ruleClass);

     /**
      * 获取分类树
      */
     List<TreeEntity> classTree();
     /**
      * 获取规则树
      */
     List<TreeEntity> ruleTree();
     /**
      * 根据规则Id获取规则参数
      * @param ruleId 规则ID
      */
     Map<String, Object> getParamsByRuleId(String ruleId);
     /**
      * 删除分类树
      */
     void delClass(Boolean isDelRule, String classId);
}
