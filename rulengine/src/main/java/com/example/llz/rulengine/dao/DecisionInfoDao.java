package com.example.llz.rulengine.dao;

import com.example.llz.rulengine.entity.DecisionInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DecisionInfoDao extends PagingAndSortingRepository<DecisionInfo, String> {
    List<DecisionInfo> findAllByRcode(String rcode);
    @Modifying
    Integer deleteAllByRcode(String rcode);
    @Modifying
    void deleteAllByRidIn(List<String> rCodes);
}
