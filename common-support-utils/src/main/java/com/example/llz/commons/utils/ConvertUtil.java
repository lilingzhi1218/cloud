package com.example.llz.commons.utils;

import com.example.llz.commons.utils.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 提供各种不同类型间的转换，一般是字符串到指定类型
 * @author dennis
 *
 */
public final class ConvertUtil
{
	
	/**
	 * 将数组转换为Set集合，不会返回null。
	 * @param <T>
	 * @param array 原数组，如果为空，则返回空的Set
	 * @return
	 */
	public static <T> HashSet<T> toHashSet(T[] array)
	{
		HashSet<T> ret=new HashSet<T>();
		if(array!=null && array.length>0) {
			for(T i:array) {
				ret.add(i);
			}
		}
		return ret;
	}
	
	/**
	 * 将数组转换为Set集合，并且排除其中的null项，不会返回null。
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> HashSet<T> toHashSetNotNull(T[] array)
	{
		HashSet<T> ret=new HashSet<T>();
		if(array!=null && array.length>0) {
			for(T i:array) {
				if(i!=null)
					ret.add(i);
			}
		}
		return ret;
	}
	
	/**
	 * 将字符串数组转换为Set集合，并且排除null或empty的项，不会返回null。
	 * @param array
	 * @return
	 */
	public static HashSet<String> toHashSetNotEmpty(String[] array)
	{
		return toHashSetNotEmpty(array,false);
	}
	
	/**
	 * 将字符串数组转换为Set集合，并且排除null或empty的项，不会返回null。
	 * @param array
	 * @param trim 是否清除前后的空白符
	 * @return
	 */
	public static HashSet<String> toHashSetNotEmpty(String[] array, boolean trim)
	{
		HashSet<String> ret=new HashSet<String>();
		if(array!=null && array.length>0) {
			for(String i:array) {
				if(i!=null && !i.isEmpty()) {
					if(trim) ret.add(i.trim());
					else ret.add(i);
				}
			}
		}
		return ret;
	}
	
	/**
	 * 创建一个Set集合，并添加一个元素，此元素如果为空，则不添加。不会返回null。
	 * @param item 要添加的非空元素
	 * @return
	 */
	public static HashSet<String> asHashSet(String item)
	{
		HashSet<String> ret=new HashSet<String>();
		if(item!=null && !item.isEmpty())
			ret.add(item);
		return ret;
	}
	

	/**
	 * 从集合中移除指定元素（如果集合是只读的，会抛异常），如果比较相同元素有多个时，会全部移除（而使用List或set的remove只移除一个）
	 * @param list
	 *          需要移除元素的字符串集合，不能为null
	 * @param target
	 *          需要移除的目标元素
	 */
	public static void removeElement(Iterable<String> list, String target)
	{
		Iterator<String> it = list.iterator();
		while(it.hasNext())
		{
			if (StringUtils.equals(it.next(), target))
			{
				it.remove();
			}
		}
	}
	
	/**
	 * 截取指定分隔符的左边子串，如果分隔符不存在，则整个字符串返回。
	 * 与{@link StringUtils#substringBefore}区别是，substringBefore在原串为null时，会返回null。
	 * @param src 要截取的字符串
	 * @param split 分隔符
	 * @return 总不为null
	 */
	public static String leftSubStr(String src,String split)
	{
		if(CheckUtil.isNullorEmpty(src)) return "";
		int ipos=src.indexOf(split);
		if(ipos>=0) return src.substring(0, ipos);
		return src;
	}
	
	/**
	 * 截取指定分隔符的右边子串，如果分隔符不存在，则返回""。
	 * 与{@link StringUtils#substringAfter}区别是，substringAfter在原串为null时，会返回null。
	 * @param src 要截取的字符串
	 * @param split 分隔符
	 * @return 总不为null
	 */
	public static String rightSubStr(String src,String split)
	{
		if(CheckUtil.isNullorEmpty(src)) return "";
		int ipos=src.indexOf(split);
		if(ipos<0) return "";
		return src.substring(ipos+1);
	}
	
	/**
	 * 将字节数组转换为十六进制表示的字符串(不够两位会补0)。
	 * @param byBuf 要转换的字节数组。
	 * @return 返回十六进制字符串
	 */
	public static String byteToString(byte[] byBuf)
	{
		return byteToString(byBuf,true);
	}
	/**
	 * 将字节数组转换为十六进制表示的字符串。
	 * @param byBuf 要转换的字节数组。
	 * @param bPrefixZero 是否带前缀0（一个字节两个字符，如果小于16时，指出是否补0对齐）。
	 * 如：值为0xA时，当此参数为true，返回"0A"；为false，返回"A"
	 * @return 转换后的字符串，如果出错，返回null
	 */
	public static String byteToString(byte[] byBuf, boolean bPrefixZero)
	{
		if(byBuf==null) return null;
		StringBuilder sb=new StringBuilder();
		String sNum;
		for (int i = 0; i < byBuf.length; i++)
		{
			sNum=Integer.toHexString(byBuf[i]&0xFF);
			if(sNum.length()==1 && bPrefixZero)
				sb.append("0");
			sb.append(sNum);
		}
		return sb.toString();
	}
	
	/**
	 * 将指定字符串重复多次，生成新字符串返回
	 * @param chr 要重复的字符（或字符串）
	 * @param iCount 重复记数（如果小于等于0，则返回""）
	 * @param split 重复间的分隔符（如果不需要，则可以设为null或""）
	 * @return
	 */
	public static String RepeatrChar(String chr, int iCount, String split)
	{
		if(iCount<=0) return "";
		if(iCount==1) return chr;
		StringBuilder sb=new StringBuilder();
		if(split==null) split="";
		for(int ix=0;ix<iCount;++ix){
			sb.append(split);
			sb.append(chr);
		}
		if(!split.isEmpty()){
			sb=sb.delete(0, split.length());
		}
		return sb.toString();
	}

	/**
	 * 将字符串内容进行CSV内容编码（类似HTML编码风格）：
	 * 回车换行符转换为&#13;&#10;，双引号转换为&quot;，逗号转换为&#44;，&符转换为&amp;
	 * @param sOrgVal 要编码的字符串
	 * @return 返回编码后的字符串，如果为空，返回Empty。
	 */
	public static String toCsvEncode(String sOrgVal)
	{
		if(CheckUtil.isNullorEmpty(sOrgVal))return "";
		return sOrgVal.replaceAll("&", "&amp;")
				.replaceAll("\\r","&#13;").replaceAll("\\n","&#10;")
				.replaceAll(",","&#44;").replaceAll("\"","&quot;");
	}
	
	/**
	 * 将以CSV编码（类似HTML编码风格）的字符串内容解码，
	 * 要解码的字符见{@code toCsvEncode}描述。
	 * @param sCsvVal 要解码的字符串
	 * @return 返回解码后的字符串，如果为空，返回Empty。
	 */
	public static String fromCsvEncode(String sCsvVal)
	{
		if(CheckUtil.isNullorEmpty(sCsvVal))return "";
		
		return sCsvVal.replaceAll("&#13;","\r").replaceAll("&#10;","\n")
				.replaceAll("&#44;",",").replaceAll("&quot;","\"")
				.replaceAll("&amp;", "&");
	}
	
	/**
	 * 将字符串转换成可做为ID使用的字符串（只包含下划线、字母、数字、或中文）<br>
	 * 原则：数字上的符号，用数字转意；纯符号键，从左到右，从上到下依次按键盘字母从左到右上下两排<br>
	 * 最后p与z结对，而多出一对符号再用最后一排字母键从左到右结对（比如：xc vb nm）。对应关系如下：<br>
	 * !@#$%^&*()   ~`   _-   +=   {[   }]   |\   :;   "'   &lt;,   &gt;.   ?/<br>
	 * 1234567890   qa   ws   ed   rf   tg   yh   uj   ik   ol   pz   xc<br>
	 * @param text 要转换的字符串
	 * @return
	 */
	public static String toIdEncode(String text)
	{
		if(CheckUtil.isNullorEmpty(text))return "";
		
		return text.replace("_", "_w")
				.replace("~", "_q")
				.replace("!", "_1").replace("@", "_2").replace("#", "_3").replace("$", "_4")
				.replace("-", "_s").replace("\\", "_h").replace(":", "_u").replace("<", "_o")
				.replace(">", "_p").replace(".", "_z")
				.replace("?", "_x").replace("/", "_c");
	}
	
	/**
	 * 将可做为ID使用的字符串转换成原字符串
	 * @param text
	 * @return
	 */
	public static String fromIdEncode(String text)
	{
		if(CheckUtil.isNullorEmpty(text))return "";
		
		return text.replace("_q", "~")
				.replace("_1", "!").replace("_2", "@").replace("_3", "#").replace("_4", "$")
				.replace("_s", "-").replace("_h", "\\").replace("_u", ":").replace("_o", "<")
				.replace("_p", ">").replace("_z", ".")
				.replace("_x", "?").replace("_c", "/")
				.replace("_w", "_");
	}
	
	/**
	 * 为了能将url放入html或js中，对特殊字符进行url编码：＜、＞、'、"、\ 共5个字符
	 * @param text
	 * @return
	 */
	public static String toUrlEncode(String text)
	{
		if(CheckUtil.isNullorEmpty(text)) return "";
		
		return text.replace("<", "%3C")
			.replace(">", "%3E").replace("'", "%27")
			.replace("\"", "%22").replace("\\", "%5C");
	}

	/**
	 * 转换为HTML编码格式常量字符串值，转换&、"、'、＜、＞、回车换行符
	 * @param text
	 * @return
	 */
	public static String toHtmlEncode(String text)
	{
		if(CheckUtil.isNullorEmpty(text))return "";
		
		return text.replace("&", "&amp;")
			.replace("\"", "&quot;").replace("'", "&apos;")
			.replace("<", "&lt;").replace(">", "&gt;")
			.replace("\r","&#13;").replace("\n","&#10;");
	}
	
	/**
	 * 移除字符串内html特殊字符，主要包括尖括号，引号(" 或 ')，斜线(\)，＆符 和 回车换行符
	 * @param text
	 * @return
	 */
	public static String removeHtmlChar(String text)
	{
		if(CheckUtil.isNullorEmpty(text)) return "";
		return text.replaceAll("[<>\\\"\\\'\\\\&\r\n]", "");
	}
	
	/**
	 * 移除字符串内 ＜script、 ＜iframe、＜frame 等有害标签，替换成＜err
	 * @param text 要处理的文本内容
	 * @return
	 */
	public static String removeScriptTag(String text)
	{
		if(CheckUtil.isNullorEmpty(text)) return "";
		return text.replaceAll("(?i)(<.?script|<.?frame|</iframe)", "<err");
	}
	
	/**
	 * 清理地址中的附带信息：去掉? # ;符号后的内容（但忽略开头为此字符的情况）。如：<br>
	 * #form/abc#this?id=123 将返回 #form/abc
	 * @param url 要处理的地址串
	 * @param retParam 如果有参数，用来返回参数信息。如果传入null将不会设置值，如果需要返回，应在调用前构造至少有三个元素的数组：<br>
	 *  如果有查询参数，将设置在retParam[0]；如果有cookie参数(包含会话id)，设置在retParam[1]；如果有锚点信息，将会设置在retParam[2]
	 * @return 清除了参数信息的地址串。如果传入的是空，返回/
	 */
	public static String removeUrlParam(String url,String[] retParam)
	{
		if(CheckUtil.isNullorEmpty(url)) return "/";
		//去除参数信息（跳过开头为?的情况，以支持vue页处理）
		int ipos=0;
		//后面接 锚点 信息（跳过开头为#的情况，以支持vue页处理）
		ipos=url.lastIndexOf('#');
		if(ipos>0) {
			if(retParam!=null && retParam.length>=3) {
				retParam[2]=url.substring(ipos+1);
			}
			url=url.substring(0,ipos);
		}
		
		ipos=url.indexOf('?',1);
		if(ipos>0){
			if(retParam!=null && retParam.length>=1) {
				retParam[0]=url.substring(ipos+1);
			}
			url=url.substring(0,ipos);
		}
		//后面接cookie信息（跳过开头为;的情况，以支持vue页处理）
		ipos=url.lastIndexOf(';');
		if(ipos>=0) {
			if(retParam!=null && retParam.length>=2) {
				retParam[1]=url.substring(ipos+1);
			}
			url=url.substring(0,ipos);
		}
		
		return url;

	}
	
	/**
	 * 转换为js常量字符串值（\、"、'、回车换行符）
	 * @param text
	 * @return
	 */
	public static String toJs(Object text){
		if(text==null) return "";
		String temp=text.toString();
		if(temp.isEmpty())return "";
		
		temp = temp.replace("\\", "\\\\")
			 .replace("\"", "\\\"").replace("'", "\\'")
			 .replace("\r", "\\r").replace("\n", "\\n");
		return temp;
	}
	
	/**
	 * 转换为sql常量字符串值（替换'为''）。
	 * @param text
	 * @return 如果为空（Empty（“”）或null），返回Empty
	 */
	public static String toSql(String text){
		if(CheckUtil.isNullorEmpty(text))
			return "";
		return text.replace("'", "''");
	}
	
	/**
	 * 移除sql语句中的字符串常量内容。主要方便后续的其它处理
	 * @param sql 要处理的sql语句，会将单引号包含的内容将每个字符替换成*。
	 * @param saveQuot 是否要保留包含内容的本单引号（内容中的单引号仍会被替换），如果为false，则单引号也会被替换。
	 * @return
	 */
	public static String removeSqlStringConst(String sql,boolean saveQuot)
	{
		if(CheckUtil.isNullorEmpty(sql)) return "";
		StringBuilder sbSql=new StringBuilder(sql.length());
		
		boolean bIn=false;
		for(int inx=0;inx<sql.length();++inx){
			char chx=sql.charAt(inx);
			if(chx!='\''){
				if(bIn)
					sbSql.append('*');
				else
					sbSql.append(chx);
			}
			else if(bIn){
				int iback=inx+1;
				if(sql.length()==iback || sql.charAt(iback)!='\''){
					sbSql.append(saveQuot?chx:'*');
					bIn=false;
				}else{
					sbSql.append("**");
					inx=iback;
				}
			}else{
				sbSql.append(saveQuot?chx:'*');
				bIn=true;
			}
		}
		return sbSql.toString();
	}
	
	/**
	 * 转换为正则表达式字符，对\、/、"、(、)等添加转义符。
	 * @param text
	 * @return
	 */
	public static String toRegex(String text)
	{
		if(CheckUtil.isNullorEmpty(text))return "";
		text = text.replace("\\", "\\\\")
			.replace("/", "\\/").replace("\"", "\\\"").replace("|", "\\|")
			.replace(".", "\\.").replace("?", "\\?").replace("*", "\\*").replace("+", "\\+")
			.replace("(", "\\(").replace(")", "\\)").replace("{", "\\{").replace("}", "\\}")
			.replace("[", "\\[").replace("]", "\\]")
			.replace("^", "\\^").replace("$", "\\$");
		return text;
	}
	
	/**
	 * 转换为字符串值。
	 * 如果为null，返回sDefault值。
	 * 如果是Date/Calendar/UUID类型，将返回标准格式的字符串；
	 * 如果是byte[]，将返回Base64编码串。
	 * 其他类型，返回toString的值
	 * @param ov
	 * @param sDefault
	 * @return
	 */
	public static String getValue(Object ov, String sDefault)
	{
		if (ov == null)
			return sDefault;
		if (ov instanceof String)
			return (String)ov;
		if (ov instanceof Date)
			return DateUtil.dateToString((Date)ov);
		if(ov instanceof Calendar)
			return DateUtil.dateToString(((Calendar)ov).getTime());
		if (ov instanceof UUID)
			return UUIDHelper.getString((UUID)ov);
		if (ov instanceof byte[])
			return Base64.getEncoder().encodeToString((byte[])ov);
		//添加clob类型的格式装换 tmg 2019-01-18
		if(ov instanceof java.sql.Clob){
			String result = sDefault;
			try{
				java.sql.Clob clob = (java.sql.Clob)ov;
				result = clob.getSubString((long)1, (int) clob.length());
			}catch(Exception e) {
				ServiceException.printStackTrace(e);
			}
			return result;
		}

		
		return ov.toString();
	}
	/**
	 * 尝试转换成整数。如果出错，返回指定默认值。
	 * Double/Float值，将截取整数部分；
	 * 
	 * @param ov
	 * @param iDefault
	 * @return
	 */
	public static int getValue(Object ov, int iDefault)
	{
		if (ov == null)
			return iDefault;
		if (ov instanceof Integer)
			return (int)ov;
		if(ov instanceof Short)
			return (short)ov;
		if(ov instanceof Long)
			return (int)(long)ov;//(dennis)可能会溢出
		if(ov instanceof BigInteger)
			return ((BigInteger)ov).intValue();
		if(ov instanceof BigDecimal)
			return ((BigDecimal)ov).intValue();
		if(ov instanceof Double)
			return (int)(double)ov;
		if(ov instanceof Float)
			return (int)(float)ov;
		if(ov instanceof Character)
			return (int)(char)ov;
		if(ov instanceof Byte)
			return (byte)ov;
		if(ov instanceof Boolean)
			return ((boolean)ov)?1:0;
		
		try
		{
			String sVal=getValue(ov, "").replace(",","");
			if (sVal.indexOf(".") >= 0)
			{
				//截断取整（不四舍五入）
				return (int)Double.parseDouble(sVal);
			}
			else
			{
				return CheckUtil.isNullorEmpty(sVal)?iDefault:Integer.parseInt(sVal);
			}
		}
		catch(Exception ex)
		{
			ServiceException.printStackTrace(ex);
			return iDefault;
		}
	}
	/**
	 * 尝试转换成长整数。如果出错，返回指定默认值。
	 * Double/Float值，将截取整数部分；
	 * 
	 * @param ov
	 * @param lDefault
	 * @return
	 */
	public static long getValue(Object ov, long lDefault)
	{
		if (ov == null)
			return lDefault;
		if (ov instanceof Integer)
			return (int)ov;
		if(ov instanceof Short)
			return (short)ov;
		if(ov instanceof Long)
			return (long)ov;
		if(ov instanceof BigInteger)
			return ((BigInteger)ov).longValue();
		if(ov instanceof BigDecimal)
			return ((BigDecimal)ov).longValue();
		if(ov instanceof Double)
			return (long)(double)ov;
		if(ov instanceof Float)
			return (long)(float)ov;
		if(ov instanceof Character)
			return (long)(char)ov;
		if(ov instanceof Byte)
			return (byte)ov;
		if(ov instanceof Boolean)
			return ((boolean)ov)?1:0;
		
		try
		{
			String sVal=getValue(ov, "").replace(",","");
			if (sVal.indexOf(".") >= 0)
			{
				//截断取整（不四舍五入）
				return (long)Double.parseDouble(sVal);
			}
			else
			{
				return CheckUtil.isNullorEmpty(sVal)?lDefault:Long.parseLong(sVal);
			}
		}
		catch(Exception ex)
		{
			ServiceException.printStackTrace(ex);
			return lDefault;
		}
	}
	/**
	 * 转换为无限大的整数。注：对于byte[]值，将调用new BigInteger(byte[])
	 * @param ov
	 * @param biDefautl
	 * @return
	 */
	public static BigInteger getValue(Object ov, BigInteger biDefautl)
	{
		if (ov == null)
			return biDefautl;
		if (ov instanceof Integer)
			return BigInteger.valueOf((int)ov);
		if(ov instanceof Short)
			return BigInteger.valueOf((short)ov);
		if(ov instanceof Long)
			return BigInteger.valueOf((long)ov);
		if(ov instanceof BigInteger)
			return (BigInteger)ov;
		if(ov instanceof BigDecimal)
			return ((BigDecimal)ov).toBigInteger();
		if(ov instanceof Double)
			return BigDecimal.valueOf((double)ov).toBigInteger();
		if(ov instanceof Float)
			return BigInteger.valueOf((long)(float)ov);
		if(ov instanceof Byte)
			return BigInteger.valueOf((byte)ov);
		if(ov instanceof Character)
			return BigInteger.valueOf((char)ov);
		if(ov instanceof Boolean)
			return BigInteger.valueOf(((boolean)ov)?1:0);
		if(ov instanceof byte[])
			return new BigInteger((byte[])ov);
		
		try
		{
			String sVal=getValue(ov, "").replace(",","");
			int iPos=sVal.indexOf(".");
			if (iPos >= 0)
			{
				//截断取整（不四舍五入）
				sVal=sVal.substring(0,iPos);
				if(sVal.isEmpty()) sVal="0";
			}
			return new BigInteger(sVal);
		}
		catch(Exception ex)
		{
			ServiceException.printStackTrace(ex);
			return biDefautl;
		}
	}
	/**
	 * 尝试转换成Double。如果出错，返回指定默认值。
	 * 
	 * @param ov
	 * @param dDefault
	 * @return
	 */
	public static double getValue(Object ov, double dDefault)
	{
		if (ov == null)
			return dDefault;
		if (ov instanceof Integer)
			return (int)ov;
		if(ov instanceof Short)
			return (short)ov;
		if(ov instanceof Long)
			return (long)ov;
		if(ov instanceof BigInteger)
			return ((BigInteger)ov).doubleValue();
		if(ov instanceof BigDecimal)
			return ((BigDecimal)ov).doubleValue();
		if(ov instanceof Double)
			return (double)ov;
		if(ov instanceof Float)
			return (float)ov;
		if(ov instanceof Character)
			return (char)ov;
		if(ov instanceof Byte)
			return (byte)ov;
		if(ov instanceof Boolean)
			return ((boolean)ov)?1.0:0.0;
		
		try
		{
			String sVal=getValue(ov, "");
			return CheckUtil.isNullorEmpty(sVal)?dDefault:Double.parseDouble(sVal);
		}
		catch(Exception ex)
		{
			ServiceException.printStackTrace(ex);
			return dDefault;
		}
	}
	
	/**
	 * 尝试转换成无限大的小数。如果出错，返回指定默认值。注：对于byte[]值，将调用new BigDecimal(new BigInteger(byte[]))
	 * 
	 * @param ov
	 * @param bdDefault
	 * @return
	 */
	public static BigDecimal getValue(Object ov, BigDecimal bdDefault)
	{
		if (ov == null)
			return bdDefault;
		if (ov instanceof Integer)
			return BigDecimal.valueOf((int)ov);
		if(ov instanceof Short)
			return BigDecimal.valueOf((short)ov);
		if(ov instanceof Long)
			return BigDecimal.valueOf((long)ov);
		if(ov instanceof BigInteger)
			return new BigDecimal((BigInteger)ov);
		if(ov instanceof BigDecimal)
			return (BigDecimal)ov;
		if(ov instanceof Double)
			return BigDecimal.valueOf((double)ov);
		if(ov instanceof Float)
			return BigDecimal.valueOf((float)ov);
		if(ov instanceof Character)
			return BigDecimal.valueOf((char)ov);
		if(ov instanceof Byte)
			return BigDecimal.valueOf((byte)ov);
		if(ov instanceof Boolean)
			return BigDecimal.valueOf(((boolean)ov)?1.0:0.0);
		if(ov instanceof byte[])
			return new BigDecimal(new BigInteger((byte[])ov));
		
		try
		{
			String sVal=getValue(ov, "");
			return new BigDecimal(sVal);
		}
		catch(Exception ex)
		{
			ServiceException.printStackTrace(ex);
			return bdDefault;
		}
	}
	/**
	 * 尝试转换成Boolean。如果出错，返回指定默认值。
	 * @param ov
	 * @param bDefault
	 * @return
	 */
	public static boolean getValue(Object ov, boolean bDefault)
	{
		if (ov == null)
			return bDefault;
		if(ov instanceof Boolean)
			return (boolean)ov;
		if (ov instanceof Integer)
			return ((int)ov)!=0;
		if(ov instanceof Short)
			return ((short)ov)!=0;
		if(ov instanceof Long)
			return ((long)ov)!=0;
		if(ov instanceof BigInteger)
			return ((BigInteger)ov).compareTo(BigInteger.ZERO)==0;
		if(ov instanceof BigDecimal)
			return ((BigDecimal)ov).compareTo(BigDecimal.ZERO)==0;
		if(ov instanceof Double)
			return ((double)ov)!=0.0;
		if(ov instanceof Float)
			return ((float)ov)!=0.0;
		if(ov instanceof Character)
			return ((char)ov)!=0;
		if(ov instanceof Byte)
			return ((byte)ov)!=0;

		try
		{
			String sVal=getValue(ov, "");
			if (sVal.isEmpty())return bDefault;
			
			return Boolean.parseBoolean(sVal);
		}
		catch(Exception ex)
		{
			ServiceException.printStackTrace(ex);
			return bDefault;
		}
	}
	
	/**
	 * 转换成日期值
	 * @param ov 可以是Date类型，Calendar类型，长整型（时间戳，从1970-1-1以来的毫秒，如果是GMT时间，需要加8小时(28800000ms)），
	 * 其它应为 可格式化日期的字符串类型（包括形如：Mon May 22 2017 21:58:42 GMT+0800 国际标准格式）。<br/>
	 * 注：如果是全数字的字符串，内部将会进行简单判断：<br/>
	 * 如果长度4位或6位或8位或14位，且月/日/时/分/秒的值在有效范围内，将视为yyyy或yyyyMM或yyyyMMdd或yyyyMMddHHmmss格式；<br/>
	 * 否则，将视为时间戳（从1970-1-1以来的毫秒）。<br/>
	 * 如：20170101 视为yyyyMMdd格式的串；<br/>
	 * 20171509 因月份值15不是有效值，则视为时间戳。<br/>
	 * 注意：如果传入的值是 java.sql.Time，转换后日期部分默认为1970-1-1，但原值有日期值，转换时也会保留；
	 *  而java.time.LocalTime类型，转换后日期部分固定为：1970-1-1。
	 * @param dtDefault
	 * @return
	 */
	public static Date getValue(Object ov, Date dtDefault)
	{
		if (ov == null)
			return dtDefault;

		if(ov instanceof java.sql.Timestamp) { //一定要在前面判断，因为Timestamp继承于Date
			return new Date(((java.sql.Timestamp)ov).getTime()); //因为用了不同的比较操作，所以需要转为Date类型
		}
		if(ov instanceof Date || ov instanceof java.sql.Date
				|| ov instanceof java.sql.Time) //java.sql.Time的日期默认为1970-1-1
			return (Date)ov;
		if(ov instanceof Calendar)
			return ((Calendar)ov).getTime();
		//LocalDate/LocalDateTime/LocalTime的转换
		if(ov instanceof LocalDate) {
			return Date.from(((LocalDate)ov).atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		if(ov instanceof LocalDateTime) {
			return Date.from(((LocalDateTime)ov).atZone(ZoneId.systemDefault()).toInstant());
		}
		if(ov instanceof LocalTime) {// 日期为1970-1-1
			return Date.from(((LocalTime)ov).atDate(LocalDate.of(1970, 1, 1)).atZone(ZoneId.systemDefault()).toInstant());
		}
		if(ov instanceof ZonedDateTime) {
			return Date.from(((ZonedDateTime)ov).toInstant());
		}
		//-------------
		if(ov instanceof Long)
			return new Date((long)ov);
		if(ov instanceof BigInteger)
			return new Date(((BigInteger)ov).longValue());
		if(ov instanceof BigDecimal)
			return new Date(((BigDecimal)ov).longValue());
		try{
			Date dt = DateUtil.stringToDate(ov.toString());
			if(dt==null)dt=dtDefault;
			return dt;
		}
		catch(Exception ex)
		{
			ServiceException.printStackTrace(ex);
			return dtDefault;
		}
	}

	/**
	 * 根据类型转换值，仅处理基本类型值，和字符串、日期值。转换失败会返回0/false/'\0'/null(String|Date)。<br>
	 * 其他类型，仅起到强制类型转换的效果，如果转换失败，会返回null。<br>
	 * 特别的，对List和Set类型，可以正确互换。
	 * <p>
	 * 需要注意，char 与 String，是不可逆的转换，char转为String后，无法转为原本的char值，反过来也是。
	 * 这是因为char转为String，会以字符表示；而String转char会以数值表示。<br>
	 * 如果要可逆，请使用int做为原值类型（强制转换），即：String sval=getValue((int)chx,String.class);
	 * </p>
	 * @param <T>
	 * @param ov
	 * @param type 转换的目标类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Object ov, Class<T> type)
	{
		Object ret=ov;
		if(type==String.class) {
			ret=getValue(ov, (String)null);
		}
		else if(type==Integer.class || type==int.class){
			ret=getValue(ov, 0);
		}else if(type==Short.class || type==short.class) {
			ret=(short)getValue(ov, 0);
		}else if(type==Byte.class || type==byte.class) {
			ret=(byte)getValue(ov, 0);
		}else if(type==Character.class || type==char.class) {
				ret=(char)getValue(ov, '\0');
		}else if(type==Long.class || type==long.class) {
			ret=getValue(ov, 0L);
		}else if(type==Boolean.class || type==boolean.class) {
			ret=getValue(ov, false);
		}else if(type==Double.class || type==double.class) {
			ret=getValue(ov,0.0);
		}else if(type==Float.class || type==float.class) {
				ret=(float)getValue(ov,0.0);
		}else if(type==BigInteger.class) {
			ret=getValue(ov, BigInteger.ZERO);
		}else if(type==BigDecimal.class) {
			ret=getValue(ov,BigDecimal.ZERO);
		}else if(type==Date.class) {
			ret=getValue(ov, (Date)null);
		}
		//#region 各种日期的转换
		else if(type==java.sql.Date.class) {
			ret=getValue(ov, (Date)null);
			if(ret!=null && !(ret instanceof java.sql.Date)) {
				ret=new java.sql.Date(((Date)ret).getTime());
			}
		}else if(type==java.sql.Time.class) {
			ret=getValue(ov, (Date)null);
			if(ret!=null && !(ret instanceof java.sql.Time)) {
				ret=new java.sql.Time(((Date)ret).getTime()); //注意：类型虽然转为了Time，但日期值仍保留，这里并没有重置为1970-1-1
			}
		}else if(type==java.sql.Timestamp.class) {
			if(!(ov instanceof java.sql.Timestamp)) {
				ret=getValue(ov, (Date)null);
				if(ret!=null && !(ret instanceof java.sql.Timestamp)) {
					ret=new java.sql.Timestamp(((Date)ret).getTime());
				}
			}
		}else if(type==LocalDate.class) {
			if(!(ov instanceof LocalDate)) {
				ret=getValue(ov, (Date)null);
				if(ret!=null) {
					ret=((Date)ret).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				}
			}
		}else if(type==LocalDateTime.class) {
			if(!(ov instanceof LocalDateTime)) {
				ret=getValue(ov, (Date)null);
				if(ret!=null) {
					ret=((Date)ret).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				}
			}
		}else if(type==LocalTime.class) {
			if(!(ov instanceof LocalTime)) {
				ret=getValue(ov, (Date)null);
				if(ret!=null) {
					ret=((Date)ret).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
				}
			}
		}else if(type==ZonedDateTime.class) {
			if(!(ov instanceof ZonedDateTime)) {
				ret=getValue(ov, (Date)null);
				if(ret!=null) {
					ret=((Date)ret).toInstant().atZone(ZoneId.systemDefault());
				}
			}
		}else if(type==Calendar.class) {
			if(!(ov instanceof Calendar)) {
				ret=getValue(ov, (Date)null);
				if(ret!=null) {
					Calendar temp=Calendar.getInstance();
					temp.setTime((Date)ret);
					ret=temp;
				}
			}
		}
		//#endregion
		else {//仅做类型转换
			ret=null;
			try {
				if(type.isInstance(ov)) {
					T temp=(T)ov;
					ret=temp;
				}else if(Set.class.isAssignableFrom(type) || Set.class==type) {//List->Set
					if(ov instanceof List) {
						Set<T> temp;
						if(type.isInterface()) {
							temp=new HashSet<>((List<T>)ov);
						} else {
							temp=(Set<T>)type.newInstance();
							temp.addAll((List<T>)ov);
						}
						ret=temp;
					}
				}else if(List.class.isAssignableFrom(type) || List.class==type) {//Set->List
					if(ov instanceof Set) {
						List<T> temp;
						if(type.isInterface()) {
							temp=new ArrayList<>((Set<T>)ov);
						} else {
							temp=(List<T>)type.newInstance();
							temp.addAll((Set<T>)ov);
						}
						ret=temp;
					}
				}
			}catch(Throwable ex) {
				ServiceException.printStackTrace(ex);
				ret=null;
			}
		}
		return (T)ret;
	}

	/**
	 * 对URL格式字符串内容进行UTF-8的URL编码（即/符号不会编码）
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String UriPartEncode(String url) throws Exception
	{
		if(CheckUtil.isNullorEmpty(url)) return "";
		
		String[] sPart=url.split("/");
		for(int ix=0;ix<sPart.length;++ix){
			sPart[ix]=URLEncoder.encode(sPart[ix], "utf-8");
		}
		
		String encUrl=String.join("/", sPart);
		//最后以/结束，也要追加一个/（因为split会移除最后的空内容）
		if(url.charAt(url.length()-1)=='/')
			encUrl+="/";
		return encUrl;
	}

}