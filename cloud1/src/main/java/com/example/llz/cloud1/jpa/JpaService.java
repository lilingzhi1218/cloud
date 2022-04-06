package com.example.llz.cloud1.jpa;

import com.example.llz.cloud1.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaService implements IJpaService {
    @Autowired
    CityDao cityDao;
    
    @Override
    public Object findAllCity(){
        return cityDao.findAll();    
    }
    
    @Override
    public void update(String name, String id){
        City city = new City();
        city.setCityId(id);
        city.setName(name);
        cityDao.save(city);
    }
}
