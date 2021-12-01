package com.example.llz.cloud1.service;

import com.alibaba.fastjson.JSON;
import com.example.llz.cloud1.dao.PersonDao;
import com.example.llz.cloud1.entity.Person;
import com.example.llz.cloud1.iService.IManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

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
        zipOutputStream.putNextEntry(new ZipEntry(name + SUFFIX_ZIP));
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
    
}
