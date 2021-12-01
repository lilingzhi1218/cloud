package com.example.llz.cloud1.controller;

import com.example.llz.commons.dbio.config.JpaDbDataUtilProperties;
import com.example.llz.commons.dbio.utils.DbDataManagerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("depend")
public class DependController {
    @Autowired
    DbDataManagerUtils dbDataManagerUtils;
    @Autowired
    JpaDbDataUtilProperties jpaDbDataUtilProperties;
    
    @RequestMapping("listDbUtilProperties")
    public void listDbUtilProperties(){
        System.out.println(jpaDbDataUtilProperties.getIgnoreClasses());
        System.out.println(jpaDbDataUtilProperties.getImportClasses());
        System.out.println(jpaDbDataUtilProperties.getLastImportClasses());
    }
}
