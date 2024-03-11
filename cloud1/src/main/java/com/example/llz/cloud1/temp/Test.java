package com.example.llz.cloud1.temp;


import com.alibaba.fastjson.JSONObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.bson.json.JsonObject;

import java.io.FileInputStream;
import java.io.IOException;

public class Test<E> extends ExtendTest<E> implements InterfaceTest1, InterfaceTest<E>{
    @Override
    void abs() {
        
    }
    @Override
    public void function() {
    }

    public static void main(String[] args) throws IOException {

        modifyPdf();
    }


    public static String PATH  ="C:\\Users\\llz\\Documents\\temp\\input.pdf";
    public static String PNG_PATH="C:\\Users\\llz\\Documents\\temp\\woman-opt.jpeg";
    public static String OUT_PATH="C:\\Users\\llz\\Documents\\temp\\output.pdf";

    public static void modifyPdf() throws IOException {

        //        1、获得PDF文件流对象
        FileInputStream pdfInputStream = new FileInputStream(PATH);
//        2、得到PDF文档对象
        PDDocument pdDocument = PDDocument.load(pdfInputStream);
//        3、由Document得到Page对象
        PDPage page = pdDocument.getPage(0);
//        4、通过图片路径和PDF文档对象得到图片image对象
        PDImageXObject image = PDImageXObject.createFromFile(PNG_PATH, pdDocument);
//        5、创建pageStream对象
        PDPageContentStream pageStream = new PDPageContentStream(pdDocument, page,PDPageContentStream.AppendMode.APPEND,false,false);

        /**
         * 可优化位置
         */
//   6、pageStream对象绘制图片位置及大小，已PDF文件右下角为原点（x,y）是图片左下角左边，width、height是图片的长和宽
        pageStream.drawImage(image, 450, 700);
        pageStream.close();
//    7、保存PDF到指定路劲
        pdDocument.save(OUT_PATH);
//      8、关闭流
        pdDocument.close();
        pdfInputStream.close();
    }
}
