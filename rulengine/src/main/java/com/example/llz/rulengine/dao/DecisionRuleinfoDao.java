package com.example.llz.rulengine.dao;


import com.example.llz.rulengine.entity.DecisionRuleinfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecisionRuleinfoDao extends PagingAndSortingRepository<DecisionRuleinfo, String>, JpaSpecificationExecutor<DecisionRuleinfo> {
    DecisionRuleinfo findByRcode(String rcode);

    List<DecisionRuleinfo> findAllByClassIdIs(String classId);

    List<DecisionRuleinfo> findAllByClassIdIsNotLikeOrderByUpdateTimeDesc(String classId);

    List<DecisionRuleinfo> findAllByClassIdInOrderByUpdateTimeDesc(List<String> classIds);

    @Query("select r.rid from DecisionRuleinfo r where r.classId in ?1")
    List<String> findRidByClassIdIn(List<String> classIds);

    List<DecisionRuleinfo> findAllByClassIdIn(List<String> classIds);
    
    @Modifying
    void deleteAllByRidIn(List<String> rCodes);
}
