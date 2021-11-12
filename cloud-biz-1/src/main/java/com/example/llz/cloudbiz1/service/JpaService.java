package com.example.llz.cloudbiz1.service;

import com.example.llz.cloudbiz1.dao.CityDao;
import com.example.llz.cloudbiz1.iService.IJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaService implements IJpaService {
    @Autowired
    CityDao cityDao;
    
    @Override
    public Object findAllCity(){
        return cityDao.findAll();    
    }
}
