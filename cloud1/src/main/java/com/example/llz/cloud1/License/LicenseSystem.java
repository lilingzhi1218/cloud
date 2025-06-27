package com.example.llz.cloud1.License;

import com.example.llz.cloud1.License.client.FeatureManager;
import com.example.llz.cloud1.License.client.LicenseValidator;
import com.example.llz.cloud1.License.management.CryptoUtils;
import com.example.llz.cloud1.License.management.LicenseData;
import com.example.llz.cloud1.License.management.LicenseGenerator;

import java.nio.charset.*;
import java.security.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LicenseSystem {

    // ====================== 示例使用 ======================
    public static void main(String[] args) throws Exception {
        // 1. 生成密钥对（在实际应用中，私钥在授权服务器，公钥在客户端）
        KeyPair keyPair = CryptoUtils.generateKeyPair();
        byte[] obfuscationKey = "SecureKey123!".getBytes(StandardCharsets.UTF_8);

        // 2. 创建许可证数据
        LicenseData licenseData = new LicenseData(
                "LIC-2025-COMPANY-A",
                "ABC科技有限公司",
                Instant.now(),
                Instant.now().plus(365, ChronoUnit.DAYS), // 1年有效期
                Arrays.asList("report_export", "data_analysis", "real_time_monitor")
        );

        // 添加硬件绑定
        licenseData.addHardwareBinding("00:1A:2B:3C:4D:5E");

        // 3. 生成许可证文件
        LicenseGenerator generator = new LicenseGenerator(keyPair.getPrivate(), obfuscationKey);
        generator.saveLicense(licenseData, "company_a_license.lic");
        System.out.println("✅ 许可证文件已生成: company_a_license.lic");

        // 4. 验证许可证
        LicenseValidator validator = new LicenseValidator(keyPair.getPublic(), obfuscationKey);
        LicenseData validatedLicense = validator.validateLicense("company_a_license.lic");
        System.out.println("✅ 许可证验证成功");
        System.out.println("   公司: " + validatedLicense.company);
        System.out.println("   有效期至: " + validatedLicense.expireAt);

        // 5. 注册功能
        FeatureManager.registerLicense(validatedLicense);
        FeatureManager.displayActiveFeatures();

        // 6. 功能检查示例
        System.out.println("报表导出功能状态: " +
                (FeatureManager.isFeatureEnabled("report_export") ? "已激活" : "未激活"));
        System.out.println("AI预测功能状态: " +
                (FeatureManager.isFeatureEnabled("ai_prediction") ? "已激活" : "未激活"));
    }
}