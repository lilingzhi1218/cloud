package com.example.llz.cloud1.License.management;

import com.example.llz.cloud1.Aspect.LogAspect;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

@RequestMapping("license")
@RestController
@LogAspect
public class LicenseController {
    @RequestMapping("generatorLicense")
    public void generatorLicense(HttpServletResponse response, @RequestBody LicenseData licenseData) throws Exception {
        response.setContentType("application/octet-stream");

        // 1. 生成密钥对（在实际应用中，私钥在授权服务器，公钥在客户端）
        PrivateKey privateKey = CryptoUtils.loadPrivateKey("C:\\Users\\llz\\Desktop\\不动产\\74638 增加授权码控制功能点\\keys\\private_key.der");
        byte[] obfuscationKey = "SouthgisSecureKey2025!".getBytes(StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + "company_a_license.lic");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        // 3. 生成许可证文件
        // 显示Base64编码的密钥
        String base64Key = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println(formatBase64(base64Key, 64));
        LicenseGenerator generator = new LicenseGenerator(privateKey, obfuscationKey);
        byte[] bytes = generator.generateLicense(licenseData);
        ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(bai, outputStream);
    }
    private static String formatBase64(String base64, int lineLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < base64.length(); i += lineLength) {
            int end = Math.min(i + lineLength, base64.length());
            sb.append(base64, i, end).append("\n");
        }
        return sb.toString().trim();
    }
}
