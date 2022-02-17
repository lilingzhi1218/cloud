package com.example.llz.rulengine.utils.assertion;

import com.southgis.ibase.utils.CheckUtil;
import com.southgis.ibase.utils.exception.base.BaseException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 异常断言接口,提供简便的方式判断条件,并在条件满足时抛出异常
 * <p>
 * 断言实现方法可参考:<br>
 * {@link org.springframework.util.Assert}
 * </p>
 * <p>
 * 错误码和错误信息在异常枚举类中定义,在本断言方法中,传递错误信息需要的参数
 * </p>
 * <p>
 * 本类主要提供三种调用：
 * <li>1) newException 返回指定类型的异常对象（由继承类实现）
 * <li>2) assertFail方法，直接抛出异常
 * <li>3) throwIsXxx方法，判断参数满足条件，则抛出异常
 * </p>
 * 
 * @author caihua
 * @date 2019/7/27
 */
public interface ExceptionAssert
{

	/**
	 * 设置异常消息占位符参数
	 * <p>
	 * 实现枚举中,需要进行重写,以便设置异常消息占位符参数后还能进行多次断言 重写示例：<br>
	 * ＠Override<br>
	 * public ExceptionAssert setPlaceholderArgs(Object... args) {<br>
	 * this.placeholderArgs = args;<br>
	 * return this;<br>
	 * }
	 * </p>
	 * 
	 * @param args
	 */
	ExceptionAssert setPlaceholderArgs(Object... args);

	/**
	 * 获取异常消息占位符参数
	 * <p>
	 * 实现枚举中,进行重写，示例：<br>
	 * ＠Override<br>
	 * public Object[] getPlaceholderArgs() {<br>
	 * return placeholderArgs;<br>
	 * }
	 * </p>
	 */
	Object[] getPlaceholderArgs();

	/**
	 * 创建异常
	 * 
	 * @param args
	 *          异常信息支持占位符参数定义,
	 * @return
	 */
	BaseException newException(Object... args);

	/**
	 * 创建异常
	 * 
	 * @param t
	 * @param args
	 * @return
	 */
	BaseException newException(Throwable t, Object... args);

	/**
	 * 直接抛出异常
	 */
	default void assertFail()
	{
		throw newException();
	}

	/**
	 * 直接抛出异常
	 * 
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default void assertFail(Object... args)
	{
		throw newException(args);
	}

	/**
	 * 直接抛出异常,并包含原异常信息
	 * <p>
	 * 当捕获非运行时异常（非继承{@link RuntimeException}）时,并该异常进行业务描述时, 必须传递原始异常,作为新异常的cause
	 * 
	 * @param t
	 *          原始异常
	 */
	default void assertFail(Throwable t)
	{
		throw newException(t);
	}

	/**
	 * 直接抛出异常,并包含原异常信息
	 * <p>
	 * 当捕获非运行时异常（非继承{@link RuntimeException}）时,并该异常进行业务描述时, 必须传递原始异常,作为新异常的cause
	 * 
	 * @param t
	 *          原始异常
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default void assertFail(Throwable t, Object... args)
	{
		throw newException(t, args);
	}

	/**
	 * 对象为空,则抛出异常（如果是字符串类型，还会判断isEmpty）
	 * 
	 * @param assertObj
	 *          待判断对象
	 */
	default ExceptionAssert throwIsNull(Object assertObj)
	{
		if (assertObj == null)
		{
			throw newException();
		}
		if (assertObj instanceof String)
		{
			throwIsNullOrEmpty((String)assertObj);
		}
		return this;
	}

	/**
	 * 对象为空则抛出异常（如果是字符串类型，还会判断isEmpty）
	 * <p>
	 * 异常信息message支持传递参数方式,避免在判断之前进行字符串拼接操作
	 * </p>
	 * 
	 * @param assertObj
	 *          待判断对象
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default ExceptionAssert throwIsNull(Object assertObj, Object... args)
	{
		if (assertObj == null)
		{
			throw newException(args);
		}
		if (assertObj instanceof String)
		{
			throwIsNullOrEmpty((String)assertObj, args);
		}
		return this.setPlaceholderArgs(args);
	}

	/**
	 * 字符串为null 或空字符串（长度为0）则抛出异常
	 * <p>
	 * 不检查空格
	 * </p>
	 * 
	 * @param assertObj
	 *          待判断字符串
	 */
	default ExceptionAssert throwIsNullOrEmpty(String assertObj)
	{
		if (CheckUtil.isNullorEmpty(assertObj))
		{
			throw newException();
		}
		return this;
	}

	/**
	 * 字符串为null 或空字符串（长度为0）则抛出异常
	 * <p>
	 * 不检查空格
	 * </p>
	 * <p>
	 * 异常信息message支持传递参数方式,避免在判断之前进行字符串拼接操作
	 * 
	 * @param assertObj
	 *          待判断字符串
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default ExceptionAssert throwIsNullOrEmpty(String assertObj, Object... args)
	{
		if (CheckUtil.isNullorEmpty(assertObj))
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}

	/**
	 * 字符串为null 或空字符串（长度为0）或仅包含空白符时，则抛出异常
	 * <p>
	 * 检查空格
	 * </p>
	 * 
	 * @param assertObj
	 *          待判断字符串
	 */
	default ExceptionAssert throwNoHasText(String assertObj)
	{
		if (StringUtils.isBlank(assertObj))
		{
			throw newException();
		}
		return this;
	}

	/**
	 * 字符串为null 或空字符串（长度为0）或仅包含空白符时，则抛出异常
	 * <p>
	 * 检查空格
	 * </p>
	 * <p>
	 * 异常信息message支持传递参数方式,避免在判断之前进行字符串拼接操作
	 * 
	 * @param assertObj
	 *          待判断字符串
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default ExceptionAssert throwNoHasText(String assertObj, Object... args)
	{
		if (StringUtils.isBlank(assertObj))
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}

	/**
	 * 数组为null或大小为0,则抛出异常
	 * 
	 * @param assertObj
	 *          待判断数组
	 */
	default ExceptionAssert throwIsEmpty(Object[] assertObj)
	{
		if (CheckUtil.isNullorEmpty(assertObj))
		{
			throw newException();
		}
		return this;
	}

	/**
	 * 数组为null或大小为0,则抛出异常
	 * <p>
	 * 异常信息message支持传递参数方式,避免在判断之前进行字符串拼接操作
	 * 
	 * @param assertObj
	 *          待判断数组
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default ExceptionAssert throwIsEmpty(Object[] assertObj, Object... args)
	{
		if (CheckUtil.isNullorEmpty(assertObj))
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}

	// /**
	// * 数组元素有null项,则抛出异常
	// * @param assertObj 待判断数组
	// */
	// default ExceptionAssert throwIsEmptyOrElementHasNull(Object[] assertObj) {
	// if (assertObj != null) {
	// for (Object element : assertObj) {
	// if (element == null) {
	// throw newException();
	// }
	// }
	// }
	// return this;
	// }
	//
	// /**
	// * 数组元素有null项,则抛出异常
	// * <p>异常信息message支持传递参数方式,避免在判断之前进行字符串拼接操作
	// * @param assertObj 待判断数组
	// * @param args message占位符对应的参数列表
	// */
	// default ExceptionAssert throwIsEmptyOrElementHasNull(Object[] assertObj, Object... args) {
	// if (assertObj != null) {
	// for (Object element : assertObj) {
	// if (element == null) {
	// throw newException(args);
	// }
	// }
	// }
	// return this.setPlaceholderArgs(args);
	// }

	/**
	 * 集合为null或者大小为0,则抛出异常
	 * 
	 * @param assertObj
	 *          待判断数组
	 */
	default ExceptionAssert throwIsEmpty(Collection<?> assertObj)
	{
		if (CheckUtil.isNullorEmpty(assertObj))
		{
			throw newException();
		}
		return this;
	}

	/**
	 * 集合为null或者大小为0,则抛出异常
	 * 
	 * @param assertObj
	 *          待判断数组
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default ExceptionAssert throwIsEmpty(Collection<?> assertObj, Object... args)
	{
		if (CheckUtil.isNullorEmpty(assertObj))
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}

	/**
	 * Map为null或者大小为0,则抛出异常
	 * 
	 * @param assertObj
	 *          待判断Map
	 */
	default ExceptionAssert throwIsEmpty(Map<?, ?> assertObj)
	{
		if (CheckUtil.isNullorEmpty(assertObj))
		{
			throw newException();
		}
		return this;
	}

	/**
	 * Map为null或者大小为0,则抛出异常
	 * 
	 * @param assertObj
	 *          待判断Map
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default ExceptionAssert throwIsEmpty(Map<?, ?> assertObj, Object... args)
	{
		if (CheckUtil.isNullorEmpty(assertObj))
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}

	/**
	 * 布尔值expression为false，则抛出异常
	 * 
	 * @param assertObj
	 *          待判断布尔变量
	 */
	default ExceptionAssert throwIsFalse(boolean assertObj)
	{
		if (!assertObj)
		{
			throw newException();
		}
		return this;
	}

	/**
	 * 断言布尔值expression为false，则抛出异常
	 * 
	 * @param assertObj
	 *          待判断布尔变量
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default ExceptionAssert throwIsFalse(boolean assertObj, Object... args)
	{
		if (!assertObj)
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}

	/**
	 * 布尔值expression为true则抛出异常
	 * 
	 * @param assertObj
	 *          待判断布尔变量
	 */
	default ExceptionAssert throwIsTrue(boolean assertObj)
	{
		if (assertObj)
		{
			throw newException();
		}
		return this;
	}

	/**
	 * 布尔值expression为true则抛出异常
	 * 
	 * @param assertObj
	 *          待判断布尔变量
	 * @param args
	 *          message占位符对应的参数列表
	 */
	default ExceptionAssert throwIsTrue(boolean assertObj, Object... args)
	{
		if (assertObj)
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}

	/**
	 * 对象obj1与对象obj2不相等（引用 或 值 都不相等）时，则抛出异常。<br>
	 * 特例：如果obj1为null，总是不抛异常，而不管obj2是否为null。<br>
	 * 如果需要obj1==null而obj2!=null时抛异常，应使用 .throwIsFalse(Objects.equals(obj1,obj2),"mmmm");
	 * @param assertObj1  待判断对象，可以为null
	 * @param assertObj2  待判断对象，可以为null
	 */
	default ExceptionAssert throwNotEquals(Object assertObj1, Object assertObj2)
	{
		if (assertObj1 != null && assertObj1 != assertObj2 && !assertObj1.equals(assertObj2))
		{
			throw newException();
		}
		return this;
	}

	/**
	 * 对象obj1与对象obj2不相等（引用 或 值 都不相等）时，则抛出异常。<br>
	 * 特例：如果obj1为null，总是不抛异常，而不管obj2是否为null。<br>
	 * 如果需要obj1==null而obj2!=null时抛异常，应使用 .throwIsFalse(Objects.equals(obj1,obj2),"mmmm");
	 * @param assertObj1  待判断对象，可以为null
	 * @param assertObj2  待判断对象，可以为null
	 * @param args message占位符对应的参数列表
	 */
	default ExceptionAssert throwNotEquals(Object assertObj1, Object assertObj2, Object... args)
	{
		if (assertObj1 != null && assertObj1 != assertObj2 && !assertObj1.equals(assertObj2))
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}

	/**
	 * 对象obj1与对象obj2相等（引用 或 值 相等）时，则抛出异常。<br>
	 * 特例：如果obj1为null，总是不抛异常，而不管obj2是否为null。<br>
	 * 如果需要都等于null时抛异常，应使用 .throwIsTrue(Objects.equals(obj1,obj2),"mmmm");
	 * @param assertObj1 待判断对象，可以为null
	 * @param assertObj2 待判断对象，可以为null
	 */
	default ExceptionAssert throwIsEquals(Object assertObj1, Object assertObj2)
	{
		if (assertObj1 != null && (assertObj1 == assertObj2 || assertObj1.equals(assertObj2)))
		{
			throw newException();
		}
		return this;
	}

	/**
	 * 对象obj1与对象obj2相等（引用 或 值 相等）时，则抛出异常。<br>
	 * 特例：如果obj1为null，总是不抛异常，而不管obj2是否为null。<br>
	 * 如果需要都等于null时抛异常，应使用 .throwIsTrue(Objects.equals(obj1,obj2),"mmmm");
	 * @param assertObj1 待判断对象，可以为null
	 * @param assertObj2 待判断对象，可以为null
	 * @param args message占位符对应的参数列表
	 */
	default ExceptionAssert throwIsEquals(Object assertObj1, Object assertObj2, Object... args)
	{
		if (assertObj1 != null && (assertObj1 == assertObj2 || assertObj1.equals(assertObj2)))
		{
			throw newException(args);
		}
		return this.setPlaceholderArgs(args);
	}
}
