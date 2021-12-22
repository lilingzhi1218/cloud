package com.example.llz.cloud2.form.dao;

import com.example.llz.cloud2.form.entity.Forminfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormInfoDao extends PagingAndSortingRepository<Forminfo, String> {
    
}
