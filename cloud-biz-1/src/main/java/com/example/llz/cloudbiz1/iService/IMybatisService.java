package com.example.llz.cloudbiz1.iService;

import com.example.llz.cloudbiz1.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface IMybatisService {
    Person findPersonByName(String name);

    Integer addPerson(Person person);
}
