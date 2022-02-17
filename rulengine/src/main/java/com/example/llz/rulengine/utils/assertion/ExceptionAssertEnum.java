package com.example.llz.rulengine.utils.assertion;

import com.southgis.ibase.utils.CheckUtil;
import com.southgis.ibase.utils.exception.ArgumentException;
import com.southgis.ibase.utils.exception.RecordDataException;
import com.southgis.ibase.utils.exception.ServiceException;
import com.southgis.ibase.utils.exception.base.BaseException;
import com.southgis.ibase.utils.exception.base.ErrorType;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 定义各种校验项，在校验失败时返回特定的异常对象。校验方法可串联调用，如：<br>
 * 所有校验方法使用同样的错误消息：
 * <pre>{@code ExceptionAssertEnum.ArgumentInvalid
 * 	.throwIsNullOrEmpty(name,"name","值为空或不正确（不能以#开头）")
 * 	.throwIsTrue(name.startsWith("#"));}
 * </pre>
 * 或每个校验方法使用各自的错误消息：<pre>{@code ExceptionAssertEnum.ArgumentInvalid
 * 	.throwIsNullOrEmpty(name,"name","值为空")
 * 	.throwIsTrue(name.startsWith("#"),"name","值不正确（不能以#开头）");}
 * </pre>
 * 在校验方法串联调用时，每个方法都可根据所调用枚举项的格式要求，单独传入错误消息内容，后一个传入的会覆盖前一个传入的内容。
 * @author dennis
 *
 */
public enum ExceptionAssertEnum implements ErrorType, ExceptionAssert
{

	/** 校验不成功时，抛出“参数无效”的ArgumentException异常。校验方法可串联调用（参见类头注释）。<br>
	 * 默认模板异常消息：“参数{0}无效：{1}”<br>
	 * 参数1为检查参数名（如果不明确可传入""），参数2为详细错误信息。调用示例：<br>
	  * <pre>{@code ExceptionAssertEnum.ArgumentInvalid
	  * 	.throwIsTrue(name.startsWith("#"),"name","值不正确（不能以#开头）");}
	  * </pre>
	 */
	ArgumentInvalid(ErrorType.EC_ARG_INVALID, "参数{0}无效：{1}") {
		@Override
		protected BaseException newException(Object[] msgArgs, String msg)
		{
			return new ArgumentException(this, msgArgs, msg);
		}
	},
	
	/** 校验不成功时，抛出“参数为空”的ArgumentException异常。校验方法可串联调用（参见类头注释）。<br>
	 * 默认模板异常消息：“参数 {0} 不能为空”<br>
	 * 参数1为需要校验的参数名。调用示例:<br>
	 * {@code ExceptionAssertEnum.ArgumentNullMsg.throwIsNull(name, "name");}
	 */
	ArgumentNull(ErrorType.EC_ARG_NULL, "参数 {0} 不能为空") {
		@Override
		protected BaseException newException(Object[] msgArgs, String msg)
		{
			return new ArgumentException(this, msgArgs, msg);
		}
	},

	/**
	 * 校验不成功时，抛出默认的ServiceException异常。校验方法可串联调用（参见类头注释）。<br>
	 * 无默认模板异常消息。由参数1定义模板异常消息的格式串<br>
	 * 参数2..n为错误消息格式化块内容（如果参数1有占位符时）。如：<br>
	 * <pre>
	 * ExceptionAssertEnum.ServiceError
	 *	.throwIsNull(user.getUserId(), "用户:{0}不能为空，登录名：{1}",
	 *	    user.getRealName(), user.getLoginName());
	 * </pre>
	 */
	ServiceError(ErrorType.EC_SERVICE_EXCEPTION,null){
		@Override
		protected BaseException newException(Object[] msgArgs, String msg)
		{
			return new ServiceException(this, msgArgs, msg);
		}
	},

	/**
	 * 校验不成功时，抛出“Http错误状态码”的ServiceException异常。校验方法可串联调用（参见类头注释）。<br>
	 * 默认模板异常消息：“http请求“{1}”，返回状态码：{0}”<br>
	 * 参数1指出错误状态码，参数2指出请求地址。如：<br>
	 * <pre>
	 * ExceptionAssertEnum.ServiceHttpError
	 *	.throwIsTrue(code>=400, code, url);
	 * </pre>
	 */
	ServiceHttpError(ErrorType.EC_SERVICE_HTTP_STATUS_CODE, "http请求“{1}”，返回状态码：{0}"){
		@Override
		protected BaseException newException(Object[] msgArgs, String msg)
		{
			return new ServiceException(this, msgArgs, msg);
		}
	},

	/**
	 * 校验不成功时，抛出“查询结果出错”的RecordDataException异常。校验方法可串联调用（参见类头注释）。<br>
	 * 无默认模板异常消息。由参数1定义模板异常消息的格式串<br>
	 * 参数2..n为错误消息格式化块内容（如果参数1有占位符时）。如：<br>
	 * <pre>
	 * ExceptionAssertEnum.RecordDataResultError
	 *	.throwIsNullOrEmpty(result, "从{0}查询结果为空", tblName);
	 * </pre>
	 */
	RecordDataResultError(ErrorType.EC_RECORD_ERROR, null){
		@Override
		protected BaseException newException(Object[] msgArgs, String msg)
		{
			return new RecordDataException(this, msgArgs, msg);
		}
	},

	/**
	 * 校验不成功时，抛出“通过RID找不到数据”的RecordDataException异常。校验方法可串联调用（参见类头注释）。<br>
	 * 默认模板异常消息：“在表字段 {1}.RID 以值“{0}”查询不到数据”<br>
	 * 参数1为RID值，参数2为查询表名。如：<br>
	 * <pre>
	 * ExceptionAssertEnum.RecordDataRidNotFound
	 *	.throwIsNullOrEmpty(value, rid, tableName);
	 * </pre>
	 */
	RecordDataRidNotFound(ErrorType.EC_RECORD_RID_NOT_FOUND, "在表字段 {1}.RID 以值“{0}”查询不到数据"){
		@Override
		protected BaseException newException(Object[] msgArgs, String msg)
		{
			return new RecordDataException(this, msgArgs, msg);
		}
	},

	/**
	 * 校验不成功时，抛出“字段类型不匹配”的RecordDataException异常。校验方法可串联调用（参见类头注释）。<br>
	 * 默认模板异常消息：“表字段{2}的类型不匹配：定义为 {1}，实际值为 {0}”<br>
	 * 参数1为值的实际类型，参数2为期望类型，参数3为表字段名。如：<br>
	 * <pre>
	 * ExceptionAssertEnum.RecordDataFieldType
	 *	.throwIsTrue(value instanceof Date, rid, tableName);
	 * </pre>
	 */
	RecordDataFieldType(ErrorType.EC_RECORD_NOT_MATCH_TYPE, "表字段{2}的类型不匹配：定义为 {1}，实际值为 {0}"){
		@Override
		protected BaseException newException(Object[] msgArgs, String msg)
		{
			return new RecordDataException(this, msgArgs, msg);
		}
	},

	/**
	 * 校验不成功时，抛出“存在重复数据”的RecordDataException异常。校验方法可串联调用（参见类头注释）。<br>
	 * 默认模板异常消息：“在{1}中已存在{0}的重复数据”<br>
	 * 参数1为用于判断重复性的数据值，参数2为判断重复数据的目标表字段名。如：<br>
	 * <pre>
	 * ExceptionAssertEnum.RecordDataDuplicate
	 *	.throwIsTrue(ret!=null && ret.size()>1, rid, tableName);
	 * </pre>
	 */
	RecordDataDuplicate(ErrorType.EC_RECORD_DUPLICATE, "在{1}中已存在{0}的重复数据"){
		@Override
		protected BaseException newException(Object[] msgArgs, String msg)
		{
			return new RecordDataException(this, msgArgs, msg);
		}
	},

	;
	/**
	 * 返回码
	 */
	private String code;
	@Override
	public String getCode()
	{
		return code;
	}

	/**
	 * 返回消息
	 */
	private String message;
	@Override
	public String getMessage()
	{
		return message;
	}

	/**
	 * 异常消息占位符参数
	 */
	private Object[] placeholderArgs;
	@Override
	public Object[] getPlaceholderArgs()
	{
		return placeholderArgs;
	}
	@Override
	public ExceptionAssert setPlaceholderArgs(Object... args)
	{
		this.placeholderArgs = args;
		return this;
	}
	//=========================================
	
	ExceptionAssertEnum(String code, String message)
	{
		this.code = code;
		this.message = message;
	}

	/**
	 * 创建ArgumentException异常
	 */
	@Override
	public BaseException newException(Object... args)
	{
		return newException(null, args);
	}

	/**
	 * 创建ArgumentException异常
	 */
	@Override
	public BaseException newException(Throwable t, Object... args)
	{
		if (CheckUtil.isNullorEmpty(args))
		{
			args = this.getPlaceholderArgs();
		}
		// 如果没有指定消息模板，则参数内第一个为消息模板
		Object[] msgArgs = args;
		String msgFormat = this.getMessage();
		if (msgFormat == null)
		{
			msgFormat = String.valueOf(args[0]);
			msgArgs = Arrays.copyOfRange(args, 1, args.length);
		}
		String msg = MessageFormat.format(msgFormat, msgArgs);
		BaseException exception = newException(msgArgs, msg);
		if(t!=null) {
			if(exception.getCause()==null)
				exception.initCause(t);
			else
				exception.addSuppressed(t);
		}
		return exception;
	}

	/**
	 * 由具体类返回合适的异常对象
	 * 
	 * @param msgArgs 传入的用于格式化错误消息的分块内容。不同异常可以取不同位置值做额外处理。
	 * @param msg 基于构造方法传入的消息格式化串格式化好的内容。
	 * @return
	 */
	protected abstract BaseException newException(Object[] msgArgs, String msg);

}
