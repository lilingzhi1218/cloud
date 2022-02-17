package com.example.llz.rulengine.utils.exception;


import com.example.llz.rulengine.utils.exception.base.BaseException;
import com.example.llz.rulengine.utils.exception.base.ErrorType;

/**
 * 参数异常
 * <p>
 * 在处理业务过程中校验参数出现错误, 可以抛出该异常
 * </p>
 * <p>
 * 编写公共代码（如工具类）时，对传入参数检查不通过时，可以抛出该异常
 * </p>
 * 
 * @author caihua
 * @date 2019/7/27
 */
public class ArgumentException extends BaseException
{

	private static final long serialVersionUID = -7583237491315513368L;

	public ArgumentException(String message)
	{
		super(message);
	}

	public ArgumentException(ErrorType errorType, Object[] args, String message)
	{
		super(errorType, args, message);
	}

	public ArgumentException(ErrorType errorType, Object[] args, String message, Throwable cause)
	{
		super(errorType, args, message, cause);
	}

	/**
	 * 获得异常数据的参数名
	 * 
	 * @return
	 */
	public String getArgumentName()
	{
		Object[] args = this.getArgs();
		return args != null && args.length > 0 ? ConvertUtil.getValue(args[0], "") : "";
	}
}
