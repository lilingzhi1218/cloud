package com.example.llz.cloudbiz1.service;

import com.example.llz.cloudbiz1.dao.PersonDao;
import com.example.llz.cloudbiz1.entity.Person;
import com.example.llz.cloudbiz1.iService.IMybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
