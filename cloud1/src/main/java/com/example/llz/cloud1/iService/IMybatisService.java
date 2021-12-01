package com.example.llz.cloud1.iService;

import com.example.llz.cloud1.entity.Person;
import com.example.llz.cloud1.entity.RelPersonCity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMybatisService {
    Person findPersonByName(String name);

    Integer addPerson(Person person);

    List<Person> findAllPerson();

    RelPersonCity findCityRelById(String id);
}
