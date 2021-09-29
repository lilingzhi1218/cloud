package com.example.llz.cloudbiz1.dao;

import com.example.llz.cloudbiz1.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PersonDao {
    
    Person findPersonByName(@Param("name")String name);
    
    Integer addPerson(Person person);
}
