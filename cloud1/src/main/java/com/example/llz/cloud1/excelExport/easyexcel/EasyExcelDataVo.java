package com.example.llz.cloud1.excelExport.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.example.llz.commons.utils.UUIDHelper;
import lombok.Data;

import java.math.RoundingMode;
import java.util.Date;

@Data
//@ExcelIgnoreUnannotated //加上该注解，只会解析加了 ExcelProperty 注解的字段
public class EasyExcelDataVo {
    @ExcelProperty(
            value = "id"//用于匹配Excel 的行头，必须全匹配，如果有多行，会匹配最后一行头
            //order = Integer.MAX_VALUE, //优先级高于 value ，会根据order 顺序来匹配实体和excel 的数据顺序
            //index = -1, //优先于 value 和 order ，会根据 index直接指定到 excel 中具体的某一列
            //converter = //指定当前字段用什么转换器，默认会自动选择写的情况下只要实现com.alibaba.excel.converters.Converter#convertToExcelData(com.alibaba.excel.converters.WriteConverterContext<T>) 方法即可
    )
    private String id;
    private String name;
    private String tel;
    private String address;
    private String email;
    @DateTimeFormat(value = "yyyy-MM-dd")
    private String birth;

    @NumberFormat(value = "#0.0" //#无数时不展示，0 无数时展示 0
            , roundingMode = RoundingMode.HALF_UP)
    private String age;

    //默认所有字段都会去匹配，加了这个注解会忽略该字段
    @ExcelIgnore
    private String a;

    public static EasyExcelDataVo getDefaultOne(){
        EasyExcelDataVo eedv = new EasyExcelDataVo();
        eedv.setId(UUIDHelper.getString());
        eedv.setName("lilingzhi");
        eedv.setTel("123456789");
        eedv.setAddress("广东广州市");
        eedv.setEmail("shdjaksdh@qq.com");
        eedv.setBirth("2088-08-08");
        eedv.setA("aaaa");
        return eedv;
    }
}
