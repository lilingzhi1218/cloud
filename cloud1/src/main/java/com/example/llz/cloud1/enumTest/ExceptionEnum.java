package com.example.llz.cloud1.enumTest;

/**
 * 主要是学习枚举类的特性和用法
 * 特性：
 * 1.枚举类可以实现多个接口、并且枚举类默认集成java.lang.Enum类，而不是Object。Enum类实现了Serializable和Comparable接口
 * 2.使用enum定义、非抽象的枚举类默认会使用final修饰，因此枚举类不能派生子类
 * *3.枚举类的所有势力必须在没剧烈的第一行显性列出，否则这个枚举类永远不能产生实例
 *
 * --定义枚举类
 * 可以把枚举类当成一个继承了java.lang.Enum的类，当定义一个枚举类时，每一个枚举成员都当成是枚举类的一个实例，
 * 这些枚举类型成员被final static public关键字修饰，要显式定义带参数的构造器
 * 
 * --实现接口的枚举类
 * 枚举类可以实现一个或者多个接口。与普通类一样，枚举类也需要实现接口中所包含的抽象方法
 * 
 * --包含抽象方法的枚举类
 * 因为枚举类需要显性定义枚举值、所以在定义枚举值时需要实现抽象方法
 */
public enum ExceptionEnum implements ExceptionAssert {
	
	ServiceError("500", "参数为空"){
		@Override
		public MyException newException() {
			return null;
		}

		@Override
		protected MyException newException(Object[] msgArgs, String msg) {
			return null;
		}
	};

	String errorType;
	String message;
	ExceptionEnum(String errorType, String message) {
		this.errorType = errorType;
		this.message = message;
	}

	ExceptionEnum(String errorType) {
		this.errorType = errorType;
	}



	/**
	 * 由具体类返回合适的异常对象
	 *
	 * @param msgArgs 传入的用于格式化错误消息的分块内容。不同异常可以取不同位置值做额外处理。
	 * @param msg 基于构造方法传入的消息格式化串格式化好的内容。
	 * @return
	 */
	protected abstract MyException newException(Object[] msgArgs, String msg);


}
