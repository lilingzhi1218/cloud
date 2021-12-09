package com.example.llz.cloud2.controller;

import com.example.llz.cloud2.feign.IManagementFeignClient;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("managment")
public class ManagementController {
    
    @Autowired
    IManagementFeignClient managementFeignClient;
    
    @RequestMapping("getStream")
    public void getStream(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sdjaskldj.zip", "UTF-8"));
        Response feignResponse = managementFeignClient.exportPersonForExcel();
        OutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        zipOutputStream.putNextEntry(new ZipEntry("ss.zip"));
        ZipOutputStream zipOutputStream1 = new ZipOutputStream(zipOutputStream);
        zipOutputStream1.putNextEntry(new ZipEntry("sub.zip"));
        zipOutputStream1.flush();
        zipOutputStream1.closeEntry();
        zipOutputStream.putNextEntry(new ZipEntry("iii.json"));
        zipOutputStream.putNextEntry(new ZipEntry("ddd.zip"));
        IOUtils.copy(feignResponse.body().asInputStream(), zipOutputStream);
        zipOutputStream.flush();
        zipOutputStream.closeEntry();
        zipOutputStream.close();

    }
}
