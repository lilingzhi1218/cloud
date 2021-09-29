package com.example.llz.cloudbiz1.dao;

import com.example.llz.cloudbiz1.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonDao {
    
    Person findPersonByName(@Param("name")String name);
    
    Integer addPerson(Person person);
    
    @Select("select * from person")
    List<Person> findAllPerson();
}
