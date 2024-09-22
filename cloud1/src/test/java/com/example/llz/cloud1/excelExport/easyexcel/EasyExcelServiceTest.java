package com.example.llz.cloud1.excelExport.easyexcel;

import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EasyExcelServiceTest {

    @Autowired
    private IEasyExcelService easyExcelService;

    @Test
    void exportPersonForExcel() {
    }

    @Test
    void importPersonForExcel() {
    }

    @Test
    void exportBigData() {
        easyExcelService.exportBigData();
    }
}