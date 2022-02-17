package com.example.llz.commons.utils.exception.base;

/**
 * 异常接口
 * 
 * @author caihua
 * @date 2019/7/27
 */
public interface ErrorType
{
	/**
	 * 获取返回码
	 * 
	 * @return 返回码
	 */
	String getCode();

	/**
	 * 获取返回信息
	 * 
	 * @return 返回信息
	 */
	String getMessage();

	/**
	 * 错误消息前包含错误代码时的分隔符，用于无法单独传递业务相关错误码的场合。
	 * 一般用于spring重定向到/error处理时使用（因为此时无法传递业务相关的错误代码）。
	 */
	public final static String ERR_SPLITOR = "::@";
	/**
	 * 系统内部异常
	 */
	public final static String EC_SYS_ERROR = "-1";// CMM_SYS_ERROR
	/**
	 * 系统繁忙
	 */
	public final static String EC_SYS_BUSY = "-2";// CMM_SYS_BUSY

	/**
	 * 未定义异常
	 */
	public final static String EC_UNDEFIND_EXCEPTION = "500";// CMM_SYS_UndefindException

	/**
	 * 参数无效
	 */
	public final static String EC_ARG_INVALID = "1000";// CMM_SYS_ArgInvalid
	 /**
	 * 参数错误
	 */
	 public final static String EC_ARG_ERROR="1001";//CMM_SYS_ArgError
	/**
	 * 参数为空错误
	 */
	public final static String EC_ARG_NULL = "1002";// CMM_SYS_ArgNull

	/**
	 * 查询结果错误
	 */
	public final static String EC_RECORD_ERROR = "2000";// CMM_SYS_RecordError
	/**
	 * 通过RID无法找到记录
	 */
	public final static String EC_RECORD_RID_NOT_FOUND = "2001";// CMM_SYS_RidNotFound
	 /**
	 * 通过指定字段值无法找到记录
	 */
	public final static String EC_RECORD_FIELD_NOT_FOUND="2002";//CMM_SYS_FieldNotFOUND

	/**
	 * 字段类型不匹配
	 */
	public final static String EC_RECORD_NOT_MATCH_TYPE = "2003";// CMM_SYS_FieldTypeNotMatch

	/**
	 * 通过某个主键查询，已存在重复记录
	 */
	public final static String EC_RECORD_DUPLICATE = "2004";// CMM_SYS_RecordDuplicate

	/**
	 * 服务内部异常
	 */
	public final static String EC_SERVICE_EXCEPTION = "3000";// CMM_SYS_ServiceException

	 /**
	 * 未注入要求的对象
	 */
	public final static String EC_SERVICE_NOT_INJECTION="3001";//CMM_SYS_ObjectNotInjection

	/**
	 * 内部Http请求返回了错误码异常
	 */
	public final static String EC_SERVICE_HTTP_STATUS_CODE = "3002";// CMM_SYS_HttpInvokeError

	
	/**
	 * 回应对象内容不可读取
	 */
	public final static String EC_HTTP_NOT_READABLE = "4001";// CMM_HTTP_ContentUnreadable

	/**
	 * 请求参数的类型不匹配
	 */
	public final static String EC_HTTP_ARG_TYPE_MISMATCH = "4002";//CMM_HTTP_ArgTypeMismatch

	/**
	 * 缺少请求参数
	 */
	public final static String EC_HTTP_ARG_MISSING = "4003";//CMM_HTTP_ArgMissing

	/**
	 * 参数校验失败错误
	 */
	public final static String EC_HTTP_ARG_VALUE_INVALID = "4004";//CMM_HTTP_ArgValueInvalid

	/**
	 * 请求方式不支持
	 */
	public final static String EC_HTTP_METHOD_NOT_SUPPORTED = "4005";//CMM_HTTP_MethodNotSupported
}
