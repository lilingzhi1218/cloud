package com.example.llz.cloud1.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.example.llz.cloud1.entity.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonListener implements ReadListener<Person> {
    
    @Override
    public void invoke(Person person, AnalysisContext analysisContext) {
        log.info("解析到一条数据：{}", JSON.toJSONString(person));
        
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
