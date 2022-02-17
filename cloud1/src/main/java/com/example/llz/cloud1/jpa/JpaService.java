package com.example.llz.cloud1.jpa;

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
