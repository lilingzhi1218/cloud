package com.example.llz.cloud1.License;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class KeyPairGeneratorDemo {

    public static void main(String[] args) {
        // 1. 生成密钥对
        KeyPair keyPair = generateRSAKeyPair(2048);

        // 2. 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 3. 保存密钥到文件
        String basePath = "keys/";
        saveKeyToFile(publicKey, basePath + "public_key.der");
        saveKeyToFile(privateKey, basePath + "private_key.der");

        // 4. 打印密钥信息
        System.out.println("===== 密钥生成成功 =====");
        System.out.println("公钥文件: " + basePath + "public_key.der");
        System.out.println("私钥文件: " + basePath + "private_key.der");
        System.out.println("\n公钥信息:");
        printKeyInfo(publicKey);
        System.out.println("\n私钥信息:");
        printKeyInfo(privateKey);
        System.out.println("\n公钥 (Base64):\n" + keyToBase64(publicKey));
    }

    /**
     * 生成RSA密钥对
     * @param keySize 密钥长度 (推荐2048或4096)
     * @return 生成的密钥对
     */
    public static KeyPair generateRSAKeyPair(int keySize) {
        try {
            // 使用RSA算法创建密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            // 初始化密钥生成器
            keyPairGenerator.initialize(keySize, new SecureRandom());

            // 生成密钥对
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA算法不可用", e);
        }
    }

    /**
     * 将密钥保存到文件
     * @param key 要保存的密钥
     * @param filePath 文件路径
     */
    public static void saveKeyToFile(Key key, String filePath) {
        try {
            // 确保目录存在
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());

            // 获取密钥的编码格式
            String format = key instanceof PublicKey ? "X.509" : "PKCS#8";
            byte[] encoded = key.getEncoded();

            // 写入文件
            Files.write(path, encoded);
            System.out.println("密钥已保存到: " + filePath + " (" + format + "格式)");
        } catch (IOException e) {
            throw new RuntimeException("保存密钥失败: " + filePath, e);
        }
    }

    /**
     * 打印密钥信息
     * @param key 要打印的密钥
     */
    public static void printKeyInfo(Key key) {
        System.out.println("算法: " + key.getAlgorithm());
        System.out.println("格式: " + key.getFormat());
        System.out.println("长度: " + (key.getEncoded().length * 8) + "位");
    }

    /**
     * 将密钥转换为Base64字符串
     * @param key 要转换的密钥
     * @return Base64编码的字符串
     */
    public static String keyToBase64(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}