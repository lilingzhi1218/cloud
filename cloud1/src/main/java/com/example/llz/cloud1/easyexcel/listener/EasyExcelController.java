package com.example.llz.cloud1.easyexcel.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Controller
@RequestMapping("easyExcel")
public class EasyExcelController {
    @Autowired
    IEasyExcelService iEasyExcelService;
    @RequestMapping("exportPersonForExcel")
    @ResponseBody
    public void exportPersonForExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
//        response.setContentType("application/zip");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + "exportPersonForExcel.zip");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        this.iEasyExcelService.exportPersonForExcel(response.getOutputStream());
    }

    @RequestMapping("importPersonForExcel")
    @ResponseBody
    public void importPersonForExcel(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()){
            throw new Exception("上传文件为空");
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv") && !file.getOriginalFilename().endsWith(".xlsx")) {
            throw new Exception("请上传csv或xlsx文件");
        }
        this.iEasyExcelService.importPersonForExcel(file.getInputStream());

    }
}
