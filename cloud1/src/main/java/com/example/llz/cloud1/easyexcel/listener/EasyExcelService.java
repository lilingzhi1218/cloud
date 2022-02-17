package com.example.llz.cloud1.easyexcel.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.llz.cloud1.entity.Person;
import com.example.llz.cloud1.mybatis.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class EasyExcelService implements IEasyExcelService {

    @Autowired
    PersonDao personDao;
    
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

    @Override
    public void importPersonForExcel(InputStream inputStream) throws IOException {
        EasyExcel.read(inputStream, Person.class, new PersonListener()).sheet().doRead();
    }

}
