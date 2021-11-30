package com.example.llz.commons.dbio.utils;


import com.example.llz.commons.dbio.support.DbDataProcessor;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.ArrayList;
import java.util.List;

public class DbDataManagerUtils {

    private final List<DbDataProcessor<?>> dbDataProcessorList;


    public DbDataManagerUtils(List<DbDataProcessor<?>> dbDataProcessorList) {
        this.dbDataProcessorList = new ArrayList<>(dbDataProcessorList);
        AnnotationAwareOrderComparator.sort(this.dbDataProcessorList);
    }
    
}
