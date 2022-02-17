package com.example.llz.rulengine.utils.exception;

import com.southgis.ibase.utils.ConvertUtil;
import com.southgis.ibase.utils.exception.base.BaseException;
import com.southgis.ibase.utils.exception.base.ErrorType;

/**
 * 当无法找到指定记录时抛出的异常.
 */
public class RecordDataException extends BaseException {

	private static final long serialVersionUID = 7794714969392533048L;

	/**
	 * 由断言类构造
	 * @param errorType
	 * @param args 第一个参数为rid值或字段信息；第二个参数为表名
	 * @param message
	 */
	public RecordDataException(ErrorType errorType, Object[] args, String message) {
		super(errorType, args, message);
	}

	/**
	 * 由断言类构造
	 * @param errorType
	 * @param args 第一个参数为rid值或字段信息；第二个参数为表名
	 * @param message
	 * @param cause
	 */
	public RecordDataException(ErrorType errorType, Object[] args, String message, Throwable cause) {
		super(errorType, args, message, cause);
	}

	/**
	 * 获取无法找到记录的表名.
	 * @return 表名，这个方法不会返回{@code null}。
	 */
	public String getTableName() {
		Object[] args=this.getArgs();
		return args!=null && args.length > 1?ConvertUtil.getValue(args[1],""):"";

	}

	/**
	 * 获取无法找到记录的字段值.
	 * @return 字段值，这个方法不会返回{@code null}。
	 */
	public String getFieldValue() {
		Object[] args=this.getArgs();
		return args!=null && args.length > 0?ConvertUtil.getValue(args[0],""):"";
	}
	
	/**
	 * 获取无法找到记录的字段名
	 * @return 如果未指定，返回“RID”字段名
	 */
	public String getFieldName() {
		Object[] args=this.getArgs();
		return args!=null && args.length > 2?ConvertUtil.getValue(args[2],"RID"):"RID";
	}
	
}
