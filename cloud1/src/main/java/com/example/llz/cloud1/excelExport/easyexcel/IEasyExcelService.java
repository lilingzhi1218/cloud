package com.example.llz.cloud1.excelExport.easyexcel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IEasyExcelService {

    void exportPersonForExcel(OutputStream outputStream) throws IOException;

    void importPersonForExcel(InputStream inputStream) throws IOException;

    void exportBigData();
}
