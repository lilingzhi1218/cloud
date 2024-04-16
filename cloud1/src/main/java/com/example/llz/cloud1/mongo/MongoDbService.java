package com.example.llz.cloud1.mongo;

import com.example.llz.cloud1.entity.Person;
import com.example.llz.cloud1.mongo.IMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoDbService implements IMongoDbService {
    //禁用mongo自动配置
//    @Autowired
    MongoTemplate mongoTemplate;
    
    @Override
    public Person insert(Person person){
        return mongoTemplate.insert(person);
    }
    
    @Override
    public Person findById(String id){
        return mongoTemplate.findById(id, Person.class);
    }
    
    @Override
    public long delById(String id){
        Person person = mongoTemplate.findById(id, Person.class);
        if (person == null) return 0;
        return mongoTemplate.remove(person).getDeletedCount();
        
    }
    
}
