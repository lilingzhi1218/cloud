package com.example.llz.cloud2.form.dao;

import com.example.llz.cloud2.form.entity.Formclass;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormClassDao extends PagingAndSortingRepository<Formclass, String> {
}
