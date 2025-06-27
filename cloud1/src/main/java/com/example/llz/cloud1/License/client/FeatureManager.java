package com.example.llz.cloud1.License.client;


import com.example.llz.cloud1.License.management.LicenseData;

import java.util.HashSet;
import java.util.Set;

// ====================== 功能管理器 ======================
public class FeatureManager {
    private static final Set<String> activeFeatures = new HashSet<>();
    private static LicenseData currentLicense;

    public static void registerLicense(LicenseData license) {
        currentLicense = license;
        activeFeatures.clear();
        activeFeatures.addAll(license.features);
    }

    public static boolean isFeatureEnabled(String feature) {
        return activeFeatures.contains(feature);
    }

    public static void displayActiveFeatures() {
        System.out.println("\n===== 激活的功能 =====");
        for (String feature : activeFeatures) {
            System.out.println("✅ " + feature);
        }
        System.out.println("=====================\n");
    }
}
