package com.example.llz.commons.dbio.config;

import lombok.Data;

import java.util.List;

@Data
public class JpaDbDataUtilProperties {
    private List<Class<?>> ignoreClasses;
    private List<Class<?>> lastImportClasses;
    private List<Class<?>> importClasses;
}
