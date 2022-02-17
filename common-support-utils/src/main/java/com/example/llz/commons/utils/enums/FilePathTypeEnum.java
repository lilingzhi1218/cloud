package com.example.llz.commons.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 文件路径类型枚举
 * @author caihua
 * @version 1.0
 * @date 2019/11/13
 */
@AllArgsConstructor
@Getter
public enum FilePathTypeEnum {

	/** 空路径 */
	EMPTY(-1, "empty"),

	/** 本地磁盘路径 */
	LOCALDISK(0, "default"),

	/** 路径以http://开头 */
	HTTP(1, "http"),

	/** 路径以https://开头 */
	HTTPS(2, "https"),

	/** 路径以ftp://开头 */
	FTP(10, "ftp"),

	/** 路径以mongo://开头 */
	MONGODB(20, "mongodb"),

	/** 路径以ctyun://开头 */
	CTYUN(30, "ctyun"),

	/** 路径以aliyun://开头 */
	ALIYUN(40, "aliyun"),

	/** 路径以minio://开头 */
	MINIO(50,"minio"),
	;

	/**
	 * 类型标识码
	 */
	private Integer code;

	/**
	 * 文件路径上的具体类型
	 */
	private String type;

	public static int getCodeByType(String type) {
		FilePathTypeEnum[] values = FilePathTypeEnum.values();
		return Arrays.stream(values)
				.filter(val -> type.equals(val.getType()))
				.map(FilePathTypeEnum::getCode)
				.findFirst()
				.orElse(EMPTY.code);
	}
}
