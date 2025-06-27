package com.example.llz.cloud1.License.management;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

@RequestMapping("license")
@RestController
public class LicenseController {
    @RequestMapping("generatorLicense")
    public void generatorLicense(HttpServletResponse response, @RequestBody LicenseData licenseData) throws Exception {
        response.setContentType("application/octet-stream");

        // 1. 生成密钥对（在实际应用中，私钥在授权服务器，公钥在客户端）
        KeyPair keyPair = CryptoUtils.generateKeyPair();
        byte[] obfuscationKey = "SecureKey123!".getBytes(StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + "company_a_license.lic");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        // 2. 创建许可证数据
//        LicenseData licenseData = new LicenseData(
//                "LIC-2023-COMPANY-A",
//                "ABC科技有限公司",
//                Instant.now(),
//                Instant.now().plus(365, ChronoUnit.DAYS), // 1年有效期
//                Arrays.asList("report_export", "data_analysis", "real_time_monitor")
//        );

        // 添加硬件绑定
        licenseData.addHardwareBinding("00:1A:2B:3C:4D:5E");

        // 3. 生成许可证文件
        LicenseGenerator generator = new LicenseGenerator(keyPair.getPrivate(), obfuscationKey);
        byte[] bytes = generator.generateLicense(licenseData);
        ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(bai, outputStream);
    }
}
