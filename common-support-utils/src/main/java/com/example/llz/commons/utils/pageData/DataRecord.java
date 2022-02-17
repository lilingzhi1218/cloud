package com.example.llz.commons.utils.pageData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**描述一条记录。
 * 字段有序的记录对象，先添加的在前面。字段名大小写无关
 * @author dennis
 *
 */
public class DataRecord extends LinkedCaseInsensitiveMap<Object>
{
	private static final long serialVersionUID = 8924652245550818586L;
	
	/**
	 * RID字段名
	 */
	public static final String FIELD_RID="RID";
	
	public static final String FIELD_ID="ID";
	
	public DataRecord()
	{
		super();
	}
	
	public DataRecord(int initialCapacity)
	{
		super(initialCapacity);
	}

	@JsonIgnore
	public Object getRid()
	{
		Object val=this.get(FIELD_RID);
		if(val==null)
			this.get(FIELD_ID);
		return val;
	}
}
