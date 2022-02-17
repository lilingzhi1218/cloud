package com.example.llz.commons.utils;


/**
 * 系统需要的一些常量定义
 * @author dennis
 *
 */
public final class Constant {
	
//	public static final String ERROR_OF_NONE = "未知错误";
//	public static final String UNKNOWN = "(未知)";
	
//	/**
//	 * 错误标准提示信息。第一个位置为服务名，第二个位置为错误信息。
//	 */
//	public static final String ERROR_PROMPT="%s 服务拒绝了本次调用：%s。%n如此问题一直出现，请联系管理员。";

//	/**
//	 * 系统内的默认编码方式
//	 */
//	public static final String DEFAULT_ENCODE = "UTF-8";

	/**
	 * 注销请求匹配地址：由过滤器处理的注销请求。过滤器会拦截以此结束的请求地址，并进行注销处理。<br>
	 * 与{@link #CUSTOM_LOGOUT}不同的是，以此结束的请求并不会传递到内部应用
	 */
	public final static String DEFAULT_LOGOUT="/public/logout";
	
	/**
	 * 注销请求匹配地址：由应用自定义处理的注销请求。过滤器会拦截以此结束的请求地址，并进行注销处理。<br>
	 * 与{@link #DEFAULT_LOGOUT}不同的是，以此结束的请求，在过滤器处理完后，会传递到内部应用。
	 */
	public final static String CUSTOM_LOGOUT="/public/slogout";
	
	/**
	 * 特殊组织项：超级管理员RID
	 */
	public static final String ORG_ADMIN_RID = "00000001-0000-0000-0010-000000000001";

	/**
	 * 特殊组织项：内部服务调用的帐号
	 */
	public static final String ORG_SERVER_INVOKE_RID = "inner-server-invoke";

	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String UPPER_TRUE = "TRUE";
	public static final String UPPER_FALSE = "FALSE";
	
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String CANCEL = "cancel";
	public static final String STOP = "stop";
	
//	public static final String CONTENT_TYPE_HTML = "text/html";
//	public static final String CONTENT_TYPE_JSON = "text/json";
//	public static final String CONTENT_TYPE_XML = "text/xml";
//	public static final String CONTENT_TYPE_PLAIN = "text/plain";

	/**
	 * 系统默认分页记录数
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;
	/**
	 * 跨域调用的客户端回调方法前缀
	 */
	public static final String DEFAULT_CALLBACK = "ibase2-xjp-cb";
	/**
	 * http请求连接超时(毫秒)
	 */
	public static final int HTTP_CONNTION_TIMEOUT=15000;
	/**
	 * http读数据超时(毫秒)
	 */
	public static final int HTTP_READ_TIMEOUT=30000;
}
