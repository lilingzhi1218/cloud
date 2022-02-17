package com.example.llz.rulengine.utils.exception;

import com.southgis.ibase.utils.ConvertUtil;
import com.southgis.ibase.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理工具类
 * @author caihua
 * @version 1.0
 * @date 2019/11/8
 */
public class ExceptionHandleUtil {

	/**
	 * 获取异常返回结果
	 * @param e
	 * @param msg 默认错误信息
	 * @param code 错误码
	 * @param msgType 返回消息类型：detail（或不设置）,表示要返回所有信息;
	 *   safe,仅返回 code、msg;
	 *   none,只返回统一的 “内部服务错误”
	 * @return [msg:显示的错误信息,error:异常对象错误信息（内部用于排查问题的错）,
	 *   code:错误代码,stack:调用栈,root:根异对象对错误信息
	 */
	public static Map<String, Object> getResultMap(Exception e, String msg, String code, String msgType) {
		Map<String, Object> resultMap = new HashMap<>();
		if (msgType.equals("none")) {
			resultMap.put("error", "内部服务错误");
			return resultMap;
		}
		
		String msgStr = ConvertUtil.removeScriptTag(e.getMessage());
		Throwable throwable = getRootCause(e);
		if (StringUtils.isBlank(msgType) || msgType.equals("detail")) {
			if(throwable!=null && throwable!=e) //如果根异常与当前异常对象相等，不需要重复输出
				resultMap.put("root", throwable.getClass().getName() + ":" + ConvertUtil.removeScriptTag(throwable.getMessage()));
			resultMap.put("stack", getStackTrace(e, false));
			resultMap.put("error", e.getClass().getName() + ":" + msgStr);
		}
		resultMap.put("code", code);
		if (msg != null) {
			resultMap.put("msg", msg);
			return resultMap;
		}

		int istart;
		if ((istart=msgStr.indexOf("[errs]"))>=0) {// && msgStr.contains("[erre]")
			int iend=msgStr.indexOf("[erre]",istart);
			if(iend<0) iend=msgStr.length();
			String message = msgStr.substring(istart + 6, iend);
			resultMap.put("msg", message);
		} else {
			resultMap.put("msg", msgStr);
		}
		return resultMap;
	}

	/**
	 * 获取最初异常对象的错误消息
	 * @param throwable
	 * @return
	 */
	public static String getCauseMessage(Throwable throwable) {
		String error;
		Throwable rootCause = getRootCause(throwable);
		if (rootCause != null) {
			error = rootCause.getLocalizedMessage();
		}else {
			error = throwable.getLocalizedMessage();
		}
		return error;
	}

	/**
	 * 获取最初异常
	 * @param throwable
	 * @return
	 */
	public static Throwable getRootCause(Throwable throwable) {
		Throwable rootCause = null;
		Throwable next = throwable;
		while (next != null) {
			rootCause = next;
			next = rootCause.getCause();
		}
		return rootCause;
	}

	/**
	 * 输出异常对象类名、错误信息及堆栈信息。也含原始异常对象类名及错误信息
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		return getStackTrace(e, true);
	}

	/**
	 * 输出堆栈信息。通过参数控制是否包含异常对象类名、错误信息及含原始异常对象类名及错误信息
	 * @param throwable
	 * @param isReturnExceptionName 是否返回异常对象名
	 * @return
	 */
	public static String getStackTrace(Throwable throwable, boolean isReturnExceptionName) {
		if (throwable == null) return "";

		StringBuilder sb = new StringBuilder();
		//异常名
		if (isReturnExceptionName) {
			sb.append(throwable.toString());
			sb.append("\n");
		}
		//调用栈信息（最多25条）
		StackTraceElement[] stack = throwable.getStackTrace();
		int proxySize = stack.length;
		int iOmitted = 0;
		//多于25条时，只输出前20条
		if (proxySize > 25) {
			iOmitted = proxySize - 20;
			proxySize = 20;
		}
		for (int i = 0; i < proxySize; i++) {
			sb.append("- ");
			sb.append(stack[i].toString());
			sb.append(",\r\n");
		}
		if (iOmitted > 0) {
			sb.append("... ");
			sb.append(iOmitted);
			sb.append(" common frames omitted\r\n");
		}

		if (isReturnExceptionName) {
			Throwable rootCause = getRootCause(throwable);
			if (getRootCause(throwable) != null) {
				sb.append("最初始的异常：");
				sb.append(rootCause.toString());
			}
		}
		return sb.toString();
	}

	/**
	 * 获取异常栈字符串信息
	 * @param e 异常
	 * @return 异常栈信息字符串
	 */
	public static String getExceptionPrintStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			e.printStackTrace(pw);
			return sw.toString();
		} finally {
			FileUtil.tryCloseStream(pw);
			FileUtil.tryCloseStream(sw);
		}
	}
}
