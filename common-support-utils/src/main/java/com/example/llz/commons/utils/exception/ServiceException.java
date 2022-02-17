package com.example.llz.commons.utils.exception;

import com.example.llz.commons.utils.exception.base.BaseException;
import com.example.llz.commons.utils.exception.base.ErrorType;
import com.example.llz.commons.utils.exception.base.SystemErrorType;
import lombok.Getter;

/**
 * Service层公用的Exception.
 */
@Getter
public class ServiceException extends BaseException {

	private static final long serialVersionUID = -81998394706841411L;
	
	/**
	 * 从错误消息内，截取需要的显示内容所在的左分隔符
	 */
	private static final String ERRMSG_SPLIT_START="[errs]";
	/**
	 * 从错误消息内，截取需要的显示内容所在的右分隔符
	 */
	private static final String ERRMSG_SPLIT_END="[erre]";

	/**
	 * 由断言实现枚举来定义错误码和错误信息
	 * @param errorType
	 * @param args
	 * @param message
	 */
	public ServiceException(ErrorType errorType, Object[] args, String message) {
		super(errorType, args, message);
	}

	/**
	 * 由断言实现枚举来定义错误码和错误信息
	 * @param errorType
	 * @param args
	 * @param message
	 */
	public ServiceException(ErrorType errorType, Object[] args, String message, Throwable cause) {
		super(errorType, args, message, cause);
	}

	/**
	 * 初始化服务异常
	 * @param code 错误码由ErrorType定义
	 * @param message
	 * @param cause
	 */
	public ServiceException(String code, String message, Throwable cause) {
		//添加err标签，让客户端更准确找到错误信息
		super(code, message, cause);
	}

	/**
	 * 业务异常默认错误code为错误码
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		this(ErrorType.EC_SERVICE_EXCEPTION, message, cause);
	}

	/**
	 * 以-1错误码初始化，错误消息为“系统异常”
	 */
	public ServiceException() {
		super(SystemErrorType.SYSTEM_ERROR);
	}

	/**
	 * 业务异常默认错误code为错误码初始化
	 * @param message 错误消息
	 */
	public ServiceException(String message) {
		this(ErrorType.EC_SERVICE_EXCEPTION, message, null);
	}

	/**
	 * 业务异常默认错误code为错误码，异常的消息内容为错误消息
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		this(ErrorType.EC_SERVICE_EXCEPTION, cause.getLocalizedMessage(), cause);
	}

	/**
	 * 获取异常错误信息。去除系统约定的分隔符（[errs],[erre]）
	 * @param errorMsg
	 * @return
	 */
	public static String getMessage(String errorMsg) {
		if (errorMsg == null) return "";
		int start=errorMsg.lastIndexOf(ERRMSG_SPLIT_START)+ERRMSG_SPLIT_START.length();
		if (start>=ERRMSG_SPLIT_START.length()) {
			int end=errorMsg.indexOf(ERRMSG_SPLIT_END,start);
			if(end>0)
				return errorMsg.substring(start+6, end);
		}
		return errorMsg;
	}

	/**
	 * 向控制台输出异常信息，是Throwable.printStackTrace的封装
	 * @param ex 异常对象
	 */
	public static void printStackTrace(Throwable ex)
	{
		printStackTrace(ex, null, true);
	}
	
	/**
	 * 向控制台输出异常信息，不输出调用栈信息
	 * @param ex 异常对象
	 * @param errMsg 要额外输出的错误信息，如果为null，则不输出；如果为""，则输出一行“[error]”
	 */
	public static void printStackTrace(Throwable ex, String errMsg)
	{
		printStackTrace(ex, errMsg, false);
	}
	/**
	 * 向控制台输出异常信息，是Throwable.printStackTrace的封装
	 * @param ex 异常对象
	 * @param errMsg 要额外输出的错误信息，如果为null，则不输出；如果为""，则输出一行“[error]”
	 * @param stackTrace 是否输出调用栈信息
	 */
	public static void printStackTrace(Throwable ex, String errMsg, boolean stackTrace)
	{
		if(errMsg!=null)
			System.err.println("[error] "+errMsg);
		if(ex!=null) {
			System.err.print("==已处理的异常输出(exception handled as output)== ");
			if(stackTrace)
				ex.printStackTrace();
			else {
				System.err.println(ex.getLocalizedMessage());
			}
		}
	}
}