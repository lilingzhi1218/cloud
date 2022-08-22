package com.example.llz.cloud1.enumTest;

public interface ExceptionAssert {
	default ExceptionAssert throwIsNull(Object assertObj) {
		if (assertObj == null)
		{
			throw newException();
		}
		return this;
	}

	MyException newException();
	
}
