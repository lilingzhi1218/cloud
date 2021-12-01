package com.example.llz.cloud1.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {
    private static final long serialVersionUID = 1201141449105680361L;
    private String id;
    private String name;
    private String age;
    private String sex;
}
