package com.example.llz.cloud1.iService;

import com.example.llz.cloud1.entity.Person;

public interface IMongoDbService {
    Person insert(Person person);

    Person findById(String id);

    long delById(String id);
}
