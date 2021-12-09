package com.example.llz.cloud1.iService;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service(IManagementService.SERVICE_BEAN_NAME)
public interface IManagementService {
    String SERVICE_BEAN_NAME = "IManagementService";
    void exportPerson(OutputStream outputStream) throws IOException;

    Object importPerson(InputStream inputStream) throws IOException;

    void exportPersonForExcel(OutputStream outputStream) throws IOException;

    void importPersonForExcel(InputStream inputStream) throws IOException;
}
