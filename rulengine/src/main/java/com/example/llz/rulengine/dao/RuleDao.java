package com.example.llz.rulengine.dao;

import com.example.llz.rulengine.entity.Ruleinfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleDao extends PagingAndSortingRepository<Ruleinfo, String> {
    Ruleinfo findByRcode(String rcode);
}
