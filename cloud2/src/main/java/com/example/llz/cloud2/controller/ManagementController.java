package com.example.llz.cloud2.controller;

import com.example.llz.cloud2.feign.IManagementFeignClient;
import feign.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.print.attribute.standard.MediaTray;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("managment")
public class ManagementController {
    
    @Autowired
    IManagementFeignClient managementFeignClient;

    /**
     * 服务间调用导出接口，获取其流数据
     * feignClient接口要返回一个feign.Response，就可以从中获取outputStream了
     * @param response
     * @throws IOException
     */
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
    /**
     * 远程调用其他微服务导入接口
     * 如果只需要上传文件中某部分，需要将multipart转成输入流，再将需要的文件新建一个multipartFile传到feign导入接口
     */
    @RequestMapping("feignImport")
    public void feignImport(MultipartFile mFile) throws IOException {
        InputStream fileInputStream = mFile.getInputStream();
        //如果是zip文件
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
        ZipEntry nextEntry = null;
        while((nextEntry = zipInputStream.getNextEntry()) != null){
            if (nextEntry.getName().endsWith(".csv") || nextEntry.getName().endsWith(".xlsx")){
                break;
            }
            if (!nextEntry.getName().equals("ddd.zip")){
                continue;
            }
            zipInputStream = new ZipInputStream(zipInputStream);
        }
        if (nextEntry != null){
            String entryName = nextEntry.getName();
            DiskFileItemFactory fac = new DiskFileItemFactory(16, null);
            FileItem fileItem = fac.createItem("file", "text/plain", true, 
                    UUID.randomUUID().toString() + entryName.substring(entryName.lastIndexOf(".")));
            OutputStream outputStream = fileItem.getOutputStream();
            IOUtils.copy(zipInputStream, outputStream);
            outputStream.close();
            this.managementFeignClient.importPersonForExcel(new CommonsMultipartFile(fileItem));
        }
        
    }
}
