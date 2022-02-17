package com.example.llz.commons.utils.json;


import com.example.llz.commons.utils.CheckUtil;
import com.example.llz.commons.utils.exception.ServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author frj
 *
 */
public class JsonUtil
{

	public static ObjectMapper getMapper(boolean ignoreUnknown)
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, !ignoreUnknown);
		return mapper;
	}
	
	/**
	 * 将结果转成Json串输出
	 *
	 * @param value 转换对象
	 * @return json字符串
	 */
	public static String toJsonString(Object value)
	{
		if(value==null) return "null";
		
		String result;
		try
		{
			result = getMapper(false).writeValueAsString(value);
		}
		catch(Exception e)
		{
			ServiceException.printStackTrace(e);
			result = "";
		}
		return result;
	}
	/**
	 * 将json串反序列化成对象，json串需要标准格式（即属性名及字符串值要以双引号包含）
	 * @param json
	 * @param classType
	 * @return
	 */
	public static Object jsonStringToObject(String json,Class<?> classType)
	{
		return jsonStringToObject(json,classType,false);
	}
	
	/**
	 * 将json串反序列化成对象，json串需要标准格式（即属性名及字符串值要以双引号包含）
	 * @param json
	 * @param classType
	 * @param ignoreUnknown 忽略该目标对象不存在的属性
	 * @return
	 */
	public static Object jsonStringToObject(String json,Class<?> classType,boolean ignoreUnknown)
	{
		Object result=null;
		if(CheckUtil.isNullorEmpty(json))
			return result;
		try
		{
			result = getMapper(ignoreUnknown).readValue(json, classType);
		}
		catch(Exception e)
		{
			ServiceException.printStackTrace(e);
			result = null;
		}
		return result;
	}
	
	/**
	 * 将字符串转换成泛型对象，比如List对象（可以用接口类型，对于List会使用ArrayList实例化，对于Map会使用LinkedHashMap实例化），
	 * json串需要标准格式（即属性名及字符串值要以双引号包含）
	 * 如：:
	 * jsonStringToObject("[{\"age\":1,\"name\":\"aa\"},{\"age\":2,\"name\":\"12\"}]",
	 *  new TypeReference＜List＜Person＞＞(){});
	 *  或
	 * jsonStringToObject("[{\"age\":1,\"name\":\"aa\"},{\"age\":2,\"name\":\"12\"}]",
	 *  new TypeReference＜List＜Map＜?,?＞＞＞(){});
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T jsonStringToObject(String json, TypeReference<T> type)
	{
		T result=null;
		if(CheckUtil.isNullorEmpty(json))
			return result;
		try
		{
			result = getMapper(false).readValue(json, type);
		}
		catch(Exception e)
		{
			ServiceException.printStackTrace(e);
			result = null;
		}
		return result;
	}
	
	/**
	 * 将字符串转换成泛型对象，比如List对象（可以用接口类型，对于List会使用ArrayList实例化，对于Map会使用LinkedHashMap实例化），
	 * json串需要标准格式（即属性名及字符串值要以双引号包含）
	 * 如：:
	 * jsonStringToObject("[{\"age\":1,\"name\":\"aa\"},{\"age\":2,\"name\":\"12\"}]",
	 *  new TypeReference＜List＜Person＞＞(){});
	 *  或
	 * jsonStringToObject("[{\"age\":1,\"name\":\"aa\"},{\"age\":2,\"name\":\"12\"}]",
	 *  new TypeReference＜List＜Map＜?,?＞＞＞(){});
	 * @param json
	 * @param type
	 * @param ignoreUnknown 是否忽略不存在的属性
	 * @return
	 */
	public static <T> T jsonStringToObject(String json,TypeReference<T> type,boolean ignoreUnknown)
	{
		T result=null;
		if(CheckUtil.isNullorEmpty(json))
			return result;
		try
		{
			result = getMapper(ignoreUnknown).readValue(json, type);
		}
		catch(Exception e)
		{
			ServiceException.printStackTrace(e);
			result = null;
		}
		return result;
	}
	
	/**
	 * 将json字符串转换为Map对象
	 * @param json
	 * @return
	 */
	public static HashMap<String, Object> jsonStringToMap(String json)
	{
		return jsonStringToObject(json,new TypeReference<HashMap<String, Object>>(){});
	}
	/**
	 * 将json字符串转换为List＜Map＞对象
	 * @param json
	 * @return
	 */
	public static ArrayList<HashMap<String, Object>> jsonStringToList(String json)
	{
		return jsonStringToObject(json,new TypeReference<ArrayList<HashMap<String, Object>>>(){});
	}

	/**
	 * 转换为通用JsonNode对象(可以进行对象属性获取，也可进行数组元素获取)，<br>
	 * 如果需要取节点下的属性值，是基本类型值，可以用JsonNode.get("属性名").asXxx()；<br>
	 * 是对象值，可以用JsonNode.get("属性名").elements()/fieldNames()/fields()得到迭代器，再遍历此节点下的每个属性。<br>
	 * 如果需要对获取的节点进行属性修改，可以判断是什么类型后，转换为
	 * com.fasterxml.jackson.databind.node 下的具体类型，再进一步处理。<br>
	 * 如果是Object，可以转换ObjectNode；<br>
	 * 如果是数组，可以转换为ArrayNode。如：<br>
	 * ((ArrayNode)jsonNode).put("age",123);<br>
	 * 另外，如果需要创建新节点，使用JsonNodeFactory.instance来创建指定类型的节点
	 * @param json
	 * @return
	 */
	public static JsonNode jsonStringToNode(String json)
	{
		JsonNode node=null;
		try{
			if(!CheckUtil.isNullorEmpty(json))
				node=getMapper(false).readTree(json);
		}
		catch(Exception e)
		{
			ServiceException.printStackTrace(e);
			node = null;
		}
		return node;
	}
}
