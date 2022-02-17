package com.example.llz.rulengine.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RuleData {
    //规则编号
    String code;
    //规则参数
    Map<String, Object> args;
}
