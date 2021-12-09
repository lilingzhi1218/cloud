package com.example.llz.cloud1.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.example.llz.cloud1.dao.PersonDao;
import com.example.llz.cloud1.entity.Person;
import com.example.llz.cloud1.iService.IManagementService;
import com.example.llz.cloud1.utils.listener.PersonListener;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service(IManagementService.SERVICE_BEAN_NAME)
public class ManagementService implements IManagementService {

    @Autowired
    PersonDao personDao;
    
    final static String SUFFIX_ZIP = ".zip";
    final static String SUFFIX_JSON = ".json";
    final static String SUFFIX_XLSX= ".xlsx";
    final static String SUFFIX_CSV= ".csv";
    
    @Override
    public void exportPerson(OutputStream outputStream) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        ZipOutputStream zipEntryStream = createAndOperateZipEntryStream("person", zipOutputStream);
        List<Person> allPerson = personDao.findAllPerson();
        for (Person person : allPerson) {
            String jsonString = JSON.toJSONString(person);
            byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
            ZipOutputStream entryStream = createAndOperateZipEntryStream(person.getName(), zipEntryStream);
            writeToPackage(person.getName(), bytes, entryStream);
        }
        zipEntryStream.close();
        zipOutputStream.close();
    }
    
    private ZipOutputStream createAndOperateZipEntryStream(String name, ZipOutputStream zipOutputStream) throws IOException{
        zipOutputStream.putNextEntry(new ZipEntry(name + SUFFIX_ZIP));
        return new ZipOutputStream(zipOutputStream);
    }
    
    private void writeToPackage(String name, byte[] bytes, ZipOutputStream zipOutputStream) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(name + SUFFIX_JSON));
        zipOutputStream.write(bytes);
        zipOutputStream.closeEntry();
        zipOutputStream.flush();
    }
    
    @Override
    public Object importPerson(InputStream inputStream) throws IOException {
        ZipInputStream zip = new ZipInputStream(inputStream, StandardCharsets.UTF_8);
        List<Object> dataList = new ArrayList<>();
        try{
            analyzeInputStream(zip, Person.class, dataList);
        } finally {
            zip.close();
            inputStream.close();
        }
        return dataList;
    }
    
    private void analyzeInputStream(ZipInputStream zip, Class<?> clazz, List<Object> dataList) throws IOException {
        ZipEntry zipEntry;
        while((zipEntry = zip.getNextEntry()) != null){
            if (zipEntry.getName().endsWith(SUFFIX_ZIP)){
                ZipInputStream zipInputStream = new ZipInputStream(zip);
                analyzeInputStream(zipInputStream, clazz, dataList);
            }
            if (zipEntry.getName().endsWith(SUFFIX_JSON)) {
                String jsonData = StreamUtils.copyToString(zip, StandardCharsets.UTF_8);
                Object obj = JSON.parseObject(jsonData, clazz);
                dataList.add(obj);
            }
            zip.closeEntry();
        }
    }
    /*
    
  我叫李凌志，2020年本科毕业于桂林理工大学，目前就职于广东南方数码科技，主要负责公司的政务服务平台的开发维护，
  期间也有三个月的时间去项目现场做定制开发，项目主要基于springCloud做的微服务框架，这次的话也是想找找其他机会。
    * */
    
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
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        zipInputStream.getNextEntry();
        EasyExcel.read(zipInputStream, Person.class, new PersonListener()).sheet().headRowNumber(1).doRead();

    }
    
}
