package com.example.llz.commons.utils.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统常见异常
 * @author caihua
 * @date 2019/8/1
 */
@Getter
@AllArgsConstructor
public enum SystemErrorType implements ErrorType {

	SYSTEM_ERROR(ErrorType.EC_SYS_ERROR, "系统异常"),
	SYSTEM_BUSY(ErrorType.EC_SYS_BUSY, "系统繁忙,请稍候再试"),

	;

	/**
	 * 错误类型码
	 */
	private String code;

	/**
	 * 错误类型描述信息
	 */
	private String message;

}
