package com.example.llz.cloud1.easyexcel.listener;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public interface IEasyExcelService {

    void exportPersonForExcel(OutputStream outputStream) throws IOException;

    void importPersonForExcel(InputStream inputStream) throws IOException;
}
