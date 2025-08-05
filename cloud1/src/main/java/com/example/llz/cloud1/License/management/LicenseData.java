package com.example.llz.cloud1.License.management;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// ====================== 许可证数据结构 ======================
public class LicenseData {
    public String licenseId;
    public String company;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    public Instant issuedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    public Instant expireAt;
    public List<String> features;
    public List<String> hardwareBindings; // 硬件绑定信息
    public List<String> organIds;

    // 空构造器用于JSON解析
    public LicenseData() {}

    public LicenseData(String licenseId, String company, Instant issuedAt,
                       Instant expireAt, List<String> features) {
        this.licenseId = licenseId;
        this.company = company;
        this.issuedAt = issuedAt;
        this.expireAt = expireAt;
        this.features = features;
        this.hardwareBindings = new ArrayList<>();
    }

    // 添加硬件绑定
    public void addHardwareBinding(String binding) {
        if (hardwareBindings == null) hardwareBindings = new ArrayList<>();
        hardwareBindings.add(binding);
    }

    // 转换为JSON字符串
    public String toJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // 注册Java 8日期时间模块
        mapper.registerModule(new JavaTimeModule());
        // 禁用时间戳格式（可选）
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(this);
    }

    // 从JSON解析
    public static LicenseData fromJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // 注册Java 8日期时间模块
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(json, LicenseData.class);
    }
}
