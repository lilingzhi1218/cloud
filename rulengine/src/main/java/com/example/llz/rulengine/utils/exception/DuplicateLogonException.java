package com.example.llz.rulengine.utils.exception;

import com.southgis.ibase.utils.exception.base.BaseException;

/**
 * 重复登录时抛出的异常，在验证登录用户时使用
 * @author 黄科天
 */
public class DuplicateLogonException extends BaseException {

	private static final long serialVersionUID = 4402573915470827957L;

	/**
	 * 构造函数.
	 * @param msg 异常消息
	 */
	public DuplicateLogonException(String msg) {
		//添加err标签，让客户端更准确找到错误信息
		super(msg);
	}
}
