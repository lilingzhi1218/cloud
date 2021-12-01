package com.example.llz.cloud1.controller;

import com.example.llz.cloud1.iService.IManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("management")
public class ManagementController {
    
    @Autowired
    IManagementService iManagementService;

    @RequestMapping("exportPerson")
    public void exportPerson(HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/zip");
        iManagementService.exportPerson(outputStream);
    }

    @RequestMapping("importPerson")
    @ResponseBody
    public Object importPerson(MultipartFile file) throws Exception{
        if (file.isEmpty()){
            throw new Exception("文件为空");
        }
        if (!file.getOriginalFilename().endsWith(".zip")){
            throw new Exception("文件类型不正确，请上传zip文件");
        }
        return iManagementService.importPerson(file.getInputStream());
    }
}
