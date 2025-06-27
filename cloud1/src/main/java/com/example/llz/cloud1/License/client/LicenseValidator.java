package com.example.llz.cloud1.License.client;


import com.example.llz.cloud1.License.management.LicenseData;
import com.example.llz.cloud1.License.management.CryptoUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.time.Instant;
import java.util.List;

// ====================== 许可证验证器 ======================
public class LicenseValidator {
    private final PublicKey publicKey;
    private final byte[] obfuscationKey;

    public LicenseValidator(PublicKey publicKey, byte[] obfuscationKey) {
        this.publicKey = publicKey;
        this.obfuscationKey = obfuscationKey;
    }

    // 验证许可证文件
    public LicenseData validateLicense(String filePath) throws Exception {
        // 1. 读取文件
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));

        // 2. 反混淆
        byte[] rawData = CryptoUtils.obfuscate(fileData, obfuscationKey);
        ByteBuffer buffer = ByteBuffer.wrap(rawData);

        // 3. 解析结构
        int signatureLength = buffer.getInt();
        byte[] signature = new byte[signatureLength];
        buffer.get(signature);

        byte[] protectedData = new byte[buffer.remaining()];
        buffer.get(protectedData);

        // 4. 验证签名
        if (!CryptoUtils.verifySignature(protectedData, signature, publicKey)) {
            throw new SecurityException("许可证签名无效，可能已被篡改");
        }

        // 5. 解析保护数据
        ByteBuffer dataBuffer = ByteBuffer.wrap(protectedData);
        long storedCrc = dataBuffer.getLong();

        byte[] jsonBytes = new byte[dataBuffer.remaining()];
        dataBuffer.get(jsonBytes);

        // 6. 验证CRC
        long calculatedCrc = CryptoUtils.calculateCrc32(jsonBytes);
        if (storedCrc != calculatedCrc) {
            throw new SecurityException("许可证数据损坏，CRC校验失败");
        }

        // 7. 解析JSON
        String json = new String(jsonBytes, StandardCharsets.UTF_8);
        LicenseData data = LicenseData.fromJson(json);

        // 8. 检查有效期
        if (Instant.now().isAfter(data.expireAt)) {
            throw new SecurityException("许可证已过期");
        }

        // 9. 检查硬件绑定（如果存在）
        if (data.hardwareBindings != null && !data.hardwareBindings.isEmpty()) {
            if (!checkHardwareBindings(data.hardwareBindings)) {
                throw new SecurityException("硬件环境不匹配，许可证无效");
            }
        }

        return data;
    }

    // 硬件绑定验证（简化版）
    private boolean checkHardwareBindings(List<String> requiredBindings) {
        // 实际实现应获取真实硬件信息
        String currentMac = "00:1A:2B:3C:4D:5E"; // 模拟MAC地址
        return requiredBindings.contains(currentMac);
    }
}
