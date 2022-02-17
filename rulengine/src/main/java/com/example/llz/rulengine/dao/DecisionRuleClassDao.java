package com.example.llz.rulengine.dao;

import com.example.llz.rulengine.entity.DecisionRuleClass;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecisionRuleClassDao extends PagingAndSortingRepository<DecisionRuleClass, String> {
    
    List<DecisionRuleClass> findAllByPRid(String pRid);
    
    @Query("select c.rid from DecisionRuleClass c where c.pRid = ?1")
    List<String> findIdsBypRid(String rid);
    
    boolean existsByClassNameAndPRid(String className, String PRid);
    
    @Modifying
    void deleteAllByRidIn(List<String> classIds);
}
