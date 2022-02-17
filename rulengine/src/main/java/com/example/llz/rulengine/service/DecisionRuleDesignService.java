package com.example.llz.rulengine.service;

import com.example.llz.commons.utils.CheckUtil;
import com.example.llz.commons.utils.UUIDHelper;
import com.example.llz.commons.utils.assertion.ExceptionAssertEnum;
import com.example.llz.commons.utils.json.JsonUtil;
import com.example.llz.commons.utils.pageData.PageQueryData;
import com.example.llz.commons.utils.treeEntity.TreeEntity;
import com.example.llz.rulengine.dao.DecisionInfoDao;
import com.example.llz.rulengine.dao.DecisionRuleClassDao;
import com.example.llz.rulengine.dao.DecisionRuleinfoDao;
import com.example.llz.rulengine.dto.DecisionRuleModel;
import com.example.llz.rulengine.entity.DecisionInfo;
import com.example.llz.rulengine.entity.DecisionRuleClass;
import com.example.llz.rulengine.entity.DecisionRuleinfo;
import com.example.llz.rulengine.iService.IDecisionRuleDesignService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 决策规则设计服务
 */
@Service
public class DecisionRuleDesignService implements IDecisionRuleDesignService {

    @Autowired
    private DecisionRuleinfoDao decisionRuleinfoDao;

    @Autowired
    private DecisionInfoDao decisionInfoDao;

    @Autowired
    private DecisionRuleClassDao decisionRuleClassDao;
    
    private static final String CLASS_ID_ALL = "#";
    private static final String CLASS_ID_NONE = "#0";
    
    @Override
    @Transactional
    public void saveOrUpdateRule(DecisionRuleModel drm) {
        //保存规则
        DecisionRuleinfo ruleinfo = drm.getDecisionRuleinfo();
        ExceptionAssertEnum.ServiceError.throwIsNull(ruleinfo, "规则为空");
        DecisionRuleinfo oldRule = decisionRuleinfoDao.findByRcode(ruleinfo.getRcode());
        if (oldRule == null) {
            ruleinfo.setRid(UUID.randomUUID().toString());
            ruleinfo.setCreateTime(new Date());
        } else {
            ExceptionAssertEnum.ServiceError.throwIsNullOrEmpty(ruleinfo.getRid(), "规则代码已存在");
        }
        ruleinfo.setUpdateTime(new Date());
        decisionRuleinfoDao.save(ruleinfo);

        //保存决策
        saveOrUpdateDecision(drm.getDecisionInfoList(), ruleinfo);
    }

    /**
     * 保存决策
     *
     * @param decisionInfoList 决策List
     * @param decisionRuleinfo 规则
     */
    @Transactional
    void saveOrUpdateDecision(List<DecisionInfo> decisionInfoList, DecisionRuleinfo decisionRuleinfo) {
        decisionInfoDao.deleteAllByRcode(decisionRuleinfo.getRcode()); 
        decisionInfoList.forEach(d -> {
            if (CheckUtil.isNullorEmpty(d.getRid())) {
                d.setRid(UUIDHelper.getString());
            }
            d.setRcode(decisionRuleinfo.getRcode());
            d.setRname(decisionRuleinfo.getRName());
        });
        decisionInfoDao.saveAll(decisionInfoList);
    }


    @Override
    public List<TreeEntity> classTree() {
        TreeEntity allClassTree = new TreeEntity(CLASS_ID_ALL, "全部分类", null, 0, null);
        allClassTree.setChildren(getChildrenClassList(allClassTree.getId()));
        TreeEntity unClassTree = new TreeEntity(CLASS_ID_NONE, "未分类", null, 1, null);
        unClassTree.setChildren(getChildrenClassList(null));
        return Arrays.asList(allClassTree, unClassTree);
    }
    
    @Override
    public List<TreeEntity> ruleTree(){
        List<TreeEntity> treeEntities = classTree();
        TreeEntity allClassTree = treeEntities.get(0);
        TreeEntity unClassTree = treeEntities.get(1);
        addRulesForTree(allClassTree);
        addRulesForTree(unClassTree);

        return Arrays.asList(allClassTree, unClassTree);
    }

    private void addRulesForTree(TreeEntity classTree) {
        List<DecisionRuleinfo> ruleList = this.decisionRuleinfoDao.findAllByClassIdIs(classTree.getId());
        TreeSet<TreeEntity> childTree = classTree.getChildren();

        for (TreeEntity ruleClass : childTree) {
            addRulesForTree(ruleClass);
        }
        
        for (DecisionRuleinfo rule : ruleList) {
            TreeEntity treeEntity = new TreeEntity();
            treeEntity.setId(rule.getRid());
            treeEntity.setParentId(classTree.getId());
            treeEntity.setText(rule.getRName());
            treeEntity.setNode(false);
            Map<String, Object> attr = new HashMap<>();
            attr.put("code", rule.getRcode());
            treeEntity.setAttributes(attr);
            childTree.add(treeEntity);
        }
        classTree.setChildren(childTree);
    }

    private TreeSet<TreeEntity> getChildrenClassList(String rid) {
        List<DecisionRuleClass> classList = this.decisionRuleClassDao.findAllByPRid(rid);
        TreeSet<TreeEntity> treeEntities = new TreeSet<>();
        for (DecisionRuleClass drc : classList) {
            TreeEntity treeEntity = new TreeEntity(drc.getRid(), drc.getClassName(), drc.getPRid(), 0, null);
            treeEntity.setChildren(getChildrenClassList(drc.getRid()));
            treeEntities.add(treeEntity);
        }
        return treeEntities;
    }
    
    @Override
    public Map<String, Object> getParamsByRuleId(String ruleId){
        ArrayList<HashMap<String, Object>> paramsList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        DecisionRuleinfo ruleinfo = this.decisionRuleinfoDao.findById(ruleId).orElse(null);
        ExceptionAssertEnum.ServiceError.throwIsNull(ruleinfo, "找不到规则");
        String parameter = ruleinfo.getParameter();
        String rcode = ruleinfo.getRcode();
        resultMap.put("ruleCode", rcode);
        if (!CheckUtil.isNullorEmpty(parameter)){
            paramsList = JsonUtil.jsonStringToList(parameter);
        }
        resultMap.put("ruleParams", paramsList);
        return resultMap;
        
    } 

    @Override
    @Transactional
    public void delClass(Boolean isDelRule, String classId) {
        ExceptionAssertEnum.ServiceError.throwIsNullOrEmpty(classId, classId + "分类不存在");
        List<String> classIds = findAllClassIds(classId);
        this.decisionRuleClassDao.deleteAllByRidIn(classIds);
        if (isDelRule){
            List<String> rids = this.decisionRuleinfoDao.findRidByClassIdIn(classIds);
            if (CheckUtil.isNullorEmpty(rids)) {
                return;
            }
            this.delDecisionRule(rids);
        }else {
            moveRulesToUnClass(classIds);
        }
    }

    /**
     * 将分类下规则移到为分类下
     * @param classIds 分类ID
     */
    private void moveRulesToUnClass(List<String> classIds) {
        List<DecisionRuleinfo> ruleinfoList = this.decisionRuleinfoDao.findAllByClassIdIn(classIds);
        if (CheckUtil.isNullorEmpty(ruleinfoList)) {
            return;
        }
        ruleinfoList.forEach(r->{
            r.setClassId(CLASS_ID_NONE);
            r.setClassName("未分类");
        });
        this.decisionRuleinfoDao.saveAll(ruleinfoList);
    }

    @Override
    public void listDecisionRules(PageQueryData<DecisionRuleinfo> pageQueryData, String classId) {

        List<DecisionRuleinfo> resultList;

        ExceptionAssertEnum.ServiceError.throwIsNullOrEmpty(classId, "分类ID为空");
        if (CLASS_ID_ALL.equals(classId)){
            resultList =  this.decisionRuleinfoDao.findAllByClassIdIsNotLikeOrderByUpdateTimeDesc(CLASS_ID_NONE);
        } else if (CLASS_ID_NONE.equals(classId)){
            resultList = this.decisionRuleinfoDao.findAllByClassIdIs(CLASS_ID_NONE);
        } else {
            resultList = this.decisionRuleinfoDao.findAllByClassIdInOrderByUpdateTimeDesc(findAllClassIds(classId));
        }
        //模糊查询
        String searchTxt = pageQueryData.getSearchTxt();
        if (!CheckUtil.isNullorEmpty(searchTxt)){
            Pattern pattern = Pattern.compile(searchTxt, Pattern.CASE_INSENSITIVE);
            resultList = resultList.stream()
                    .filter(r-> pattern.matcher(r.getRcode()).find() || pattern.matcher(r.getRName()).find())
                    .collect(Collectors.toList());
        }
        int total = resultList.size();
        //分页
        resultList = resultList.stream()
                .skip(pageQueryData.getRows() * (pageQueryData.getPage() - 1))
                .limit(pageQueryData.getRows()).collect(Collectors.toList());

        pageQueryData.setTotal(total);
        pageQueryData.setQueryList(resultList);
    }

    /**
     * 返回分类下的所有分类id（包括自己）
     * @param classId 分类id
     * @return 分类IdList
     */
    public List<String> findAllClassIds(String classId){
        List<String> allClassIds = new ArrayList<>();
        allClassIds.add(classId);
        allClassIds.addAll(findAllChildClassIds(classId));
        return allClassIds;
    }
    public List<String> findAllChildClassIds(String classId){
        List<String> subClassIds = this.decisionRuleClassDao.findIdsBypRid(classId);
        ArrayList<String> list = Lists.newArrayList();
        for (String id : subClassIds) {
            list.addAll(findAllChildClassIds(id));
        }
        return list;
    }

    @Override
    public DecisionRuleModel getDecisionRule(String ruleId) {
        DecisionRuleModel model = new DecisionRuleModel();
        DecisionRuleinfo ruleinfo = this.decisionRuleinfoDao.findById(ruleId).orElse(null);
        ExceptionAssertEnum.ServiceError.throwIsNull(ruleinfo, "找不到规则" + ruleId);
        model.setDecisionRuleinfo(ruleinfo);
        model.setDecisionInfoList(this.decisionInfoDao.findAllByRcode(ruleinfo.getRcode()));
        return model;
    }

    @Override
    @Transactional
    public void delDecisionRule(List<String> ruleIds) {
        ExceptionAssertEnum.ServiceError.throwIsTrue(CheckUtil.isNullorEmpty(ruleIds), "规则代码参数为空");
        this.decisionRuleinfoDao.deleteAllByRidIn(ruleIds);
        this.decisionInfoDao.deleteAllByRidIn(ruleIds);
    }

    @Override
    public String saveClass(DecisionRuleClass ruleClass) {
        String classId = ruleClass.getRid();
        if (CheckUtil.isNullorEmpty(classId)) {
            //新建
            boolean exists = this.decisionRuleClassDao.existsByClassNameAndPRid(ruleClass.getClassName(), ruleClass.getPRid());
            ExceptionAssertEnum.ServiceError.throwIsTrue(exists, "当前分类下已存在相同名的分类");
            ruleClass.setRid(UUIDHelper.getString());
        }
        DecisionRuleClass pClass = this.decisionRuleClassDao.findById(ruleClass.getPRid()).orElse(null);
        ExceptionAssertEnum.ServiceError.throwIsTrue(pClass == null && CLASS_ID_ALL.equals(classId), "父级分类不存在");
        this.decisionRuleClassDao.save(ruleClass);
        return ruleClass.getRid();
    }
}
