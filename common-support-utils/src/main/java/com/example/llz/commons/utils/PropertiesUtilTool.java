package com.example.llz.commons.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 配合PropertiesUtil类的使用，是更基础的封装，没有太多依赖，可用于任何需要的地方。
 * @author dennis
 *
 */
public final class PropertiesUtilTool
{
	/**
	 * 规范化属性配置名：将驼峰写法转为-，下划线转为.，大写转小写。
	 * @param key 要处理的配置名
	 * @return 示例如下：<br>
	 * 原值          规范化后的值<br>
	 * NAME-PART_SET  name-part.set<br>
	 * namePart.set   name-part.set<br>
	 * name-part.set  name-part.set
	 * 
	 */
	public static String normalPropertyKey(String key)
	{
		List<String> partsName=getPropertyPartName(key);
		List<String> fullNames=getFullName(partsName,0);
		return fullNames.get(0);
	}

	/**
	 * 将属性名按-或首字母大写，拆分为多部分（同时如果全存在_而没有.时，会将_替换为.），为重新组装标准写法做准备。<br>
	 * 但对于一些老的配置名包含有_（如：hibernate中的配置名“default_schema”），
	 * 又要在linux环境变理中配置的话，则会存在冲突（因为linux环境变量不支持.）。
	 * @param confName
	 * @return 示例如下：<br>
	 * 原值：name-part.otherSet_more<br>
	 * 拆分后返回：<br>
	 * [0]=name
	 * [1]=part.other
	 * [2]set.more
	 */
	public static List<String> getPropertyPartName(String confName)
	{
		List<String> partName=new ArrayList<>();
		int start=0;
		int end=0;
		confName=toDotExceptHibernate(confName);
		if(isAllUpper(confName)) //全大写的情况，以中划线分隔，肯定不是驼峰写法
			confName=confName.toLowerCase();
		for(end=0;end<confName.length();end++) {
			char chx=confName.charAt(end);
			if(chx=='-') {
				if(end>start)
					partName.add(confName.substring(start,end).toLowerCase());
				start=end+1;
			}else if(chx>='A' && chx<='Z') {
				if(end>start) //注：如果配置名中第一个字符是大写，
					partName.add(confName.substring(start,end).toLowerCase());
				start=end;
			}
		}
		if(start<end)
			partName.add(confName.substring(start).toLowerCase());
		
		return partName;
	}
	/** 
	 * 将_分隔转换为.分隔。同时注意要排除hibernate的配置名（因为包含了_）
	 */
	private static String toDotExceptHibernate(String confName)
	{
		if(confName.indexOf('.')>=0) {//有.在其中，就不做_替换工作
			return confName;
		}

		final String hiber="hibernate";
		String temp=confName.toLowerCase();
		int ipos=temp.lastIndexOf(hiber);
		if(ipos<0) //未找到hibernate关键词，直接替换
			return confName.replace("_", ".");
		
		String suffix="";
		char chx;
		if(ipos==0 || ((chx=confName.charAt(ipos-1))=='.' || chx=='_')) {
			ipos+=hiber.length();
			//是正确的配置名，则后面部分不处理
			if(ipos<confName.length() && ((chx=confName.charAt(ipos))=='.' || chx=='_')) { //找到了hibernate的属性配置
				ipos++;
				suffix=confName.substring(ipos);
				confName=confName.substring(0,ipos);
			}
		}
		return confName.replace("_", ".")+suffix;
	}

	private static boolean isAllUpper(String confName)
	{
		if(confName.isEmpty()) return false;
		for(int inx=0;inx<confName.length();inx++) {
			char chx=confName.charAt(inx);
			if(chx>='a' && chx<='z')
				return false;
		}
		
		return true;
	}
	
	/**
	 * 将拆分的属性名，按标准写法重新生成全配置名。
	 * @param partName 调用getPropertyPartName得到的拆分内容（每一部分都应是小写字母，否则会存在不致问题）
	 * @param humpCount 指出有几个用驼峰写法
	 * @return 示例如下：<br>
	 * partName：[0]=name，[1]=part.other，[2]set.more<br>
	 * humpCount=1时，返回结果：<br>
	 * [0]=namePart.other-set.more<br>
	 * [1]=name-part.otherSet.more
	 */
	public static List<String> getFullName(List<String> partName, int humpCount)
	{
		List<String> allName=new ArrayList<>();
		String curName=String.join("-", partName);
		StringBuilder sb=new StringBuilder(curName);
		if(humpCount==0) {
			allName.add(curName);
		}else {
			formatName(allName,sb,humpCount,0);//递归处理，要替换为驼峰写法的个数
		}
		return allName;
	}

	private static void formatName(List<String> allName, StringBuilder sb, int humpCount, int offset)
	{
		if(humpCount==0) {
			StringBuilder sbFull=new StringBuilder();
			int start=0,end=0;
			while((end=sb.indexOf("|",start))>=0) {
				sbFull.append(sb.substring(start, end));
				start=end+1;
				char chx=sb.charAt(start);
				sbFull.append((char)(chx-'a'+'A'));
				start++;
			}
			if(start<sb.length())
				sbFull.append(sb.substring(start));
			
			allName.add(sbFull.toString());
			return;
		}
		
		int nextOff=offset;
		String holder;
		holder="|";
		while(nextOff<sb.length()) {
			nextOff=sb.indexOf("-",nextOff);
			if(nextOff<0)
				break;
			
			sb.replace(nextOff, nextOff+1, holder);
			formatName(allName, sb, humpCount-1,nextOff);
			sb.replace(nextOff, nextOff+holder.length(), "-");
			nextOff++;
		}
	}

}
