package com.example.llz.cloudbiz1.iService;

import com.example.llz.cloudbiz1.entity.Person;

public interface IMongoDbService {
    Person insert(Person person);

    Person findById(String id);

    long delById(String id);
}
