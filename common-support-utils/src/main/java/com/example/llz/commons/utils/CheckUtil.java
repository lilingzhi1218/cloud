package com.example.llz.commons.utils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 值格式检查，以及合法性检查
 * */
public final class CheckUtil
{
	private static Pattern pattern=null;
	/**
	 * 检查字符串是否为邮件地址格式。<br>
	 * 用户名部分：以字母或数字开头，中间可包含-_.|~? 等符号，并以字母或数字结尾；<br>
	 * 域名部分：以字母或数字开头，中间可包含-. 等符号，并以字母或数字结尾（但最后一个.后不能是数字）。
	 * 
	 * @param sAddr  要检查的字符串
	 * @return true/false 是否为邮件地址
	 */
	public static boolean isEmail(String sAddr)
	{
		if (sAddr != null)
		{
			if(pattern==null) {
				pattern = Pattern
						.compile("^([a-z0-9A-Z]+[-|~?_.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z][a-z0-9A-Z]*$");
			}
			return pattern.matcher(sAddr).matches();
		}
		return false;
	}

	/**
	 * 判断可遍历集合是否包含指定元素
	 * @param list 集合
	 * @param item 指定元素
	 * @return 返回true、false
	 */
	public static <T> boolean contains(Enumeration<T> list, T item)
	{
		if(list==null) return false;
		while(list.hasMoreElements()){
			Object oVal=list.nextElement();
			//对象引用相同，或同为null
			if(oVal==item){
				return true;
			}else if(oVal!=null && oVal.equals(item)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 忽略大小写比较，判断可遍历集合是否包含指定元素
	 * @param list
	 * @param item
	 * @return
	 */
	public static boolean containsIgnoreCase(Enumeration<String> list, String item)
	{
		if(list==null) return false;
		while(list.hasMoreElements()){
			String oVal=list.nextElement();
			//对象引用相同，或同为null
			if(oVal==item){
				return true;
			}else if(oVal!=null && oVal.equalsIgnoreCase(item)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 忽略大小写比较，判断集合是否包含指定元素
	 * @param list
	 * @param item
	 * @return
	 */
	public static boolean containsIgnoreCase(Collection<String> list, String item)
	{
		if(list==null) return false;
		if(list.contains(item))
			return true;
		
		for(String val:list){
			//对象引用相同，或同为null
			if(val==item){
				return true;
			}else if(val!=null && val.equalsIgnoreCase(item)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 忽略大小写比较，判断Map集合是否包含指定Key
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean containsKeyIgnoreCase(Map<?,?> map, String key)
	{
		if(map==null) return false;
		
		if(map.containsKey(key))
			return true;
		
		for(Object orgKey:map.keySet()){
			//对象引用相同，或同为null
			if(orgKey==key){
				return true;
			}else if(orgKey!=null && orgKey.toString().equalsIgnoreCase(key)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断数组是否包含指定元素，可使用{@link org.apache.commons.lang3.ArrayUtils#contains}代替。<br>
	 * 如果需要判断字符串且不区分大小写时，可间接使用{@link #indexOfIgnoreCase(String[], String)}
	 * @param arr  数组
	 * @param item  指定元素
	 * @return true/false 是否包含
	 */
	public static <T> boolean contains(T[] arr, T item)
	{
		if(arr==null) return false;
		for(T oVal : arr){
			//对象引用相同，或同为null
			if(oVal==item){
				return true;
			}else if(oVal!=null && oVal.equals(item)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 找出字符串数组内的指定元素所在索引位置，可使用{@link org.apache.commons.lang3.ArrayUtils#indexOf}代替
	 * @param list  字符串数组
	 * @param item 指定元素
	 * @return 返回该元素所在位置
	 */
	public static int indexOf(String[] list, String item)
	{
		if(list==null || list.length==0) return -1;
		for(int ix=0;ix<list.length;++ix){
			if(list[ix]==item){
				return ix;
			}
			else if(list[ix]!=null && list[ix].equals(item))
				return ix;
		}
		return -1;
	}
	
	/**
	 * 忽略大小写，找出字符串数组内的指定元素所在索引位置
	 * @param list
	 * @param item
	 * @return
	 */
	public static int indexOfIgnoreCase(String[] list, String item)
	{
		if(list==null || list.length==0)
			return -1;
		
		for(int ix=0;ix<list.length;++ix){
			if(list[ix]==item){
				return ix;
			}
			else if(list[ix]!=null && list[ix].equalsIgnoreCase(item))
				return ix;
		}
		return -1;
	}

	/**
	 * 从Map中获取值，忽略大小写。如果未找到，返回null
	 * @param <T>
	 * @param map
	 * @param key
	 * @return
	 */
	public static <T> T getMapValueIgnoreCase(Map<?,T> map, String key)
	{
		if(map==null)
			return null;
		
		T val=map.get(key);
		if(val!=null)
			return val;
		
		for(Map.Entry<?, T> ent:map.entrySet()) {
			Object orgKey=ent.getKey();
			if(orgKey==key) {
				return ent.getValue();
			}else if(orgKey!=null && orgKey.toString().equalsIgnoreCase(key)) {
				return ent.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 检果是否为空或""字符串，可使用{@link org.apache.commons.lang3.StringUtils#isEmpty}代替
	 * @param value 要检查的字符串
	 * @return 返回结果，是否为空
	 */
	public static boolean isNullorEmpty(String value)
	{
		return value == null || value.isEmpty();
	}
	
	/**
	 * 检查集合是否为空（null 或 empty），可使用{@link org.apache.commons.collections4.CollectionUtils#isEmpty}代替
	 * @param value 要检查的集合
	 * @return 返回集合是否为空
	 */
	public static boolean isNullorEmpty(Collection<?> value)
	{
		return value == null || value.isEmpty();
	}
	
	/**
	 * 检查Map是否为空（null 或 empty），可使用{@link org.apache.commons.collections4.MapUtils#isEmpty}代替
	 * @param value  要检查的map
	 * @return 返回map是否为空
	 */
	public static boolean isNullorEmpty(Map<?,?> value)
	{
		return value == null || value.isEmpty();
	}
	
	/**
	 * 检查数组是否为空（null或长度为0），可使用{@link org.apache.commons.lang3.ArrayUtils#isEmpty(Object[])}代替
	 * @param <T> 指可用于任何数组类型的比较
	 * @param value  待检查的数组
	 * @return 返回是否为空
	 */
	public static <T> boolean isNullorEmpty(T[] value)
	{
		return value==null || value.length==0;
	}
	
	/**
	 * 检查字节数组是否为空（null或长度为0），可使用{@link org.apache.commons.lang3.ArrayUtils#isEmpty(byte[])}代替
	 * @param value  字节数组
	 * @return 是否为空
	 */
	public static boolean isNullorEmpty(byte[] value)
	{
		return value==null || value.length==0;
	}
	
	/**
	 * 检查整型数组是否为空（null或长度为0），可使用{@link org.apache.commons.lang3.ArrayUtils#isEmpty(int[])}代替
	 * @param value
	 * @return
	 */
	public static boolean isNullorEmpty(int[] value)
	{
		return value==null || value.length==0;
	}
	
	/**
	 * 判断一个数值封装类是否为null或值为0
	 * @param value 封装类
	 * @return  返回结果
	 */
	public static <T extends Number> boolean isNullorZero(T value)
	{
		return value==null || (value.doubleValue()==0);
	}

	
	/**
	 * 判断是否有效ID串。不为空，且不以#开头。
	 * @param id 要判断的标识值。
	 * @return 返回结果
	 */
	public static boolean isValidId(String id)
	{
		if(id==null || id.isEmpty()) return false;
		return id.charAt(0) != '#';
	}
	
	/**
	 * 判断查询参数的比较符，是否为like符，或=号。一般用于id查找的sql语句。
	 * @param sql 要判断的原始语句
	 * @param pn 参数名。传入一般的值有“?”、“?n”（n为序号的查询参数，如：?1）、“:name”（name为命名的查询参数，如：:uid）
	 * @return 0，两者都不是，1是like符，-1是等号
	 */
	@SuppressWarnings("deprecation")
	public static int isWhereLikeOrEquals(String sql,String pn)
	{
		int iret=0;
    if(sql!=null){
    	if(sql.indexOf('\'')>=0)
    		sql=ConvertUtil.removeSqlStringConst(sql,false);
    	
    	int ipos=sql.lastIndexOf(pn)-1;
    	while(ipos>=0){
    		if(Character.isSpace(sql.charAt(ipos))) ipos--;
    		else if(sql.charAt(ipos)=='=') {iret=-1; break;}
    		else if(isLike(sql,ipos)) {iret=1; break;}
    		else ipos=sql.lastIndexOf(pn,ipos)-1;
    	}
    }
    return iret;
	}
  //从指定位置向前推，是like关键字
  @SuppressWarnings("deprecation")
	private static boolean isLike(String sql, int ipos)
	{
		if(ipos<5) return false;
		if(!Character.isSpace(sql.charAt(ipos-4))) return false;
		char chx=sql.charAt(ipos);
		if(chx!='e' && chx!='E') return false;
		return "lik".equalsIgnoreCase(sql.substring(ipos - 3, ipos));
	}

	/**
	 * 判断是否为中文字符
	 * 
	 * @param chv
	 * @return
	 */
	public static boolean isCNumber(char chv)
	{
		// 由于汉字0可能会用到两个编码中的一个，需要全部判断
		return (chv == '○' || chv == '一' || chv == '二' || chv == '三'
				|| chv == '四' || chv == '五' || chv == '六' || chv == '七'
				|| chv == '八' || chv == '九' || chv == '〇');
	}
	
	/**
	 * 判断指定字符串是否为全数字
	 * @param val
	 * @return
	 */
	public static boolean isDigit(String val)
	{
		if(val==null || val.isEmpty()) return false;
		for(char chx:val.toCharArray()){
			if(!Character.isDigit(chx)) return false;
		}
		return true;
	}
	
	/**
	 * 收集时间差，向控件台输出时长。格式：<br>
	 * “[当前调用方法名](调用层次)title用时：时长 ms”
	 * @param start 开始时间点（等于0表示第一次调用，不输出）。
	 * @param title 要显示时间的标题文本（start=0时，如果为null则不输出，否则输出开始计时的提示）。
	 * 控件台输出内容为："XXXX用时：yyyms"。XXXX为title内容，yyy为当前时间点与start时间点的差值
	 * @return 下一次计算的开始时间点（做为下一次调用的start参数值）
	 */
	public static long checkTime(long start,String title)
	{
		long next=System.currentTimeMillis();
		StackTraceElement[] ste=Thread.currentThread().getStackTrace();
		// *#if DEBUG
		if(start>0){
			//0=getStackTrace,1=当前方法,2=调用当前方法的方法
			System.err.printf("[%s](%03d)%s用时：%d ms\n",
					ste[2].getMethodName(), ste.length, title, (next-start));
		}else if(title!=null) {
			System.err.printf("[%s](%03d)%s开始计时================================\n",
					ste[2].getMethodName(), ste.length, title);
		}
		//#endif */
		return next;
	}
	
	/** 
   * 判断某个类里是否有某个public方法，可使用{@link org.apache.commons.lang3.ClassUtils#getPublicMethod}代替
   * @param clazz 类
   * @param methodName 方法名
   * @return
   */
	public static boolean isHaveSuchMethod(Class<?> clazz, String methodName){
		Method[] methodArray = clazz.getMethods();
		boolean result = false;
		for (Method method : methodArray) {
			if (method.getName().equals(methodName)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
  /**
   * 判断指定字符串是否合法日期
   * @param datevalue 日期格式的字符串
   * @param dateFormat 判断合法日期的格式，例如yyyy-MM-dd或yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static boolean isValidDate(String datevalue, String dateFormat) {
		boolean convertSuccess=true;
		//根据指定日期格式校验
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2018-02-29会被接受，并转换成2018-03-01
			format.setLenient(false);
			format.parse(datevalue);
		} catch (ParseException e) {
			//如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess=false;
		}
		return convertSuccess;
	}
	
}
