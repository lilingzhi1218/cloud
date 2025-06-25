package com.example.llz.cloud1.jpa;

import com.example.llz.cloud1.entity.City;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class JpaService implements IJpaService {

    @Autowired
    private final CityDao cityDao;

    @Autowired
    private CountryDao chinaDao;

    //实体管理
    @Autowired
    private EntityManager entityManager;

    //查询工厂
    private JPAQueryFactory queryFactory;

    //初始化查询工厂
    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Object findAllCity() {
        chinaDao.findCountry();
        return cityDao.findAll();
    }

    @Override
    public void update(String name, String id) {
        City city = new City();
        city.setCityId(id);
        city.setName(name);
        cityDao.save(city);
    }
}
