package com.example.llz.rulengine.enums;

import java.util.Collection;
import java.util.Date;

public enum ParamTypeEnum {
    NUM("number", Number.class),
    COLLECTION("array", Collection.class),
    STR("string", String.class),
    DATE("date", Date.class);
    String typeFlag;
    Class<?> clazz;
    ParamTypeEnum(String typeFlag, Class<?> clazz){
        this.typeFlag = typeFlag;
        this.clazz = clazz;
    }
    public static Class<?> getClazz(String typeFlag){
        for (ParamTypeEnum value : ParamTypeEnum.values()) {
            if (value.getTypeFlag().equals(typeFlag)) {
                return value.getClazz();
            }
        }
        return null;
    }

    public String getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
