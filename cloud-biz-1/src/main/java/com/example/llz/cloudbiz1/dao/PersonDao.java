package com.example.llz.cloudbiz1.dao;

import com.example.llz.cloudbiz1.entity.Person;
import com.example.llz.cloudbiz1.entity.RelPersonCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonDao{
    
    Person findPersonByName(@Param("name")String name);
    
    Integer addPerson(Person person);
    
    @Select("select * from person")
    @Results({
            @Result(property = "age",  column = "age"),
            @Result(property = "name", column = "name"),
            @Result(property = "id", column = "id"),
            @Result(property = "sex", column = "id")
    })
    List<Person> findAllPerson();

    @Select("select * from rel_person_city where rel_id = #{rel_id}")
    RelPersonCity findRelPersonCityById(@Param("rel_id") String Id);
}
