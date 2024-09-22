package com.example.llz.cloud1.excelExport.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.llz.cloud1.entity.Person;
import com.example.llz.cloud1.mybatis.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class EasyExcelService implements IEasyExcelService {

    @Autowired
    PersonDao personDao;

    /**
     * 简单导出 java 对象样例
     * @param outputStream 输出流
     * @throws IOException
     */
    @Override
    public void exportPersonForExcel(OutputStream outputStream) throws IOException {
//        zipOutputStream.putNextEntry(new ZipEntry("人员" + SUFFIX_ZIP));
//        ZipOutputStream personOutputStream = new ZipOutputStream(zipOutputStream);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            zipOutputStream.putNextEntry(new ZipEntry("人员.xlsx"));
            List<Person> allPerson = this.personDao.findAllPerson();
            //流会自动关闭
//        EasyExcel.write(byteArrayOutputStream, Person.class).sheet("person").doWrite(allPerson);
            ExcelWriter excelWriter = EasyExcel.write(byteArrayOutputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("person").head(Person.class).build();
            excelWriter.write(allPerson, writeSheet);
            excelWriter.finish();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//            int len;
//            byte[] buf = new byte[1024];
//            while ((len = inputStream.read(buf)) > 0) {
//                zipOutputStream.write(buf, 0, len);
//            }
            //等于以上代码
            IoUtils.copy(inputStream, zipOutputStream);
        }
    }

    /**
     * 简单按照 java 对象导入数据
     * @param inputStream 输入流
     */
    @Override
    public void importPersonForExcel(InputStream inputStream) {
        EasyExcel.read(inputStream, Person.class, new PersonListener()).sheet().doRead();
    }


    /**
     * 使用模板填充的方式导出
     */
    @Override
    public void exportBigData(){

        List<EasyExcelDataVo> list = new ArrayList<>();
        for (int i=0; i<10000; i++){
            list.add(EasyExcelDataVo.getDefaultOne());
        }
        EasyExcel.write(new File("cloud1/src/main/resources/test.xlsx"))
                .withTemplate("cloud1/src/main/resources/模板.xlsx").sheet("sheet1").doFill(list);
    }

    public static void main(String[] args) {
        EasyExcelService easyExcelService = new EasyExcelService();
        easyExcelService.exportBigData();
    }

}
