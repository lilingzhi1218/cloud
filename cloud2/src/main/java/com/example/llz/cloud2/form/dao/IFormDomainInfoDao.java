package com.example.llz.cloud2.form.dao;

import com.example.llz.cloud2.form.entity.Formdomaininfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFormDomainInfoDao extends PagingAndSortingRepository<Formdomaininfo, String> {
    
    @Query(nativeQuery = true, value = "select SQL_NO_CACHE * from formdomaininfo t where t.rid like ?1 ")
    List<Formdomaininfo> findByCondition(String condition);
}
