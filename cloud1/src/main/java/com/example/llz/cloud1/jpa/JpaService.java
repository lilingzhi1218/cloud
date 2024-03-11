package com.example.llz.cloud1.jpa;

import com.alibaba.fastjson.JSONObject;
import com.example.llz.cloud1.entity.City;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.inject.Inject;

@Service
@RequiredArgsConstructor
public class JpaService implements IJpaService {
    
    private final CityDao cityDao;
    
    @Autowired
    private CountryDao chinaDao;
    
    @Override
    public Object findAllCity(){
        chinaDao.findCountry();
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
