package com.example.llz.cloud1.License.management;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;

// ====================== 许可证生成器 ======================
public class LicenseGenerator {
    private final PrivateKey privateKey;
    private final byte[] obfuscationKey;

    public LicenseGenerator(PrivateKey privateKey, byte[] obfuscationKey) {
        this.privateKey = privateKey;
        this.obfuscationKey = obfuscationKey;
    }

    // 生成许可证文件内容
    public byte[] generateLicense(LicenseData data) throws Exception {
        // 1. 转换为JSON
        String json = data.toJson();
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);

        // 2. 添加CRC32校验码
        long crcValue = CryptoUtils.calculateCrc32(jsonBytes);
        ByteBuffer buffer = ByteBuffer.allocate(jsonBytes.length + 8);
        buffer.putLong(crcValue);
        buffer.put(jsonBytes);
        byte[] protectedData = buffer.array();

        // 3. 生成数字签名
        byte[] signature = CryptoUtils.signData(protectedData, privateKey);

        // 4. 组合数据：长度(4字节) + 签名 + 数据
        ByteBuffer finalBuffer = ByteBuffer.allocate(4 + signature.length + protectedData.length);
        finalBuffer.putInt(signature.length);
        finalBuffer.put(signature);
        finalBuffer.put(protectedData);
        byte[] rawLicense = finalBuffer.array();

        // 5. 数据混淆
        return CryptoUtils.obfuscate(rawLicense, obfuscationKey);
    }

    // 生成并保存许可证文件
    public void saveLicense(LicenseData data, String filePath) throws Exception {
        byte[] licenseData = generateLicense(data);
        Files.write(Paths.get(filePath), licenseData);
    }
}
