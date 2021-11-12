package com.example.llz.cloudbiz1.service;

import com.example.llz.cloudbiz1.dao.PersonDao;
import com.example.llz.cloudbiz1.entity.Person;
import com.example.llz.cloudbiz1.entity.RelPersonCity;
import com.example.llz.cloudbiz1.iService.IMybatisService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MybatisService implements IMybatisService {
    @Autowired
    PersonDao personDao;
    
    @Override
    public Person findPersonByName(String name){
        return personDao.findPersonByName(name);
    }

    @Override
    public Integer addPerson(Person person){
        return personDao.addPerson(person);
    }

    @Override
    public List<Person> findAllPerson(){
        return personDao.findAllPerson();
    }
    
    @Override
    public RelPersonCity findCityRelById(String id){
        return personDao.findRelPersonCityById(id);
    }
}
