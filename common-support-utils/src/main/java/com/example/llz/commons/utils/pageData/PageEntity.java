package com.example.llz.commons.utils.pageData;

import com.example.llz.commons.utils.CheckUtil;
import com.example.llz.commons.utils.Constant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;

/**
 * 定义分页信息，并返回查询结果。
 * page ， rows 以及total参数，为兼容easyUI控件
 * @author wujian
 */
@ApiModel(value = "定义分页信息")
public class PageEntity<T> {
	/**
	 * 当前页面 ，基于1开始
	 */
	@ApiModelProperty(value = "当前页面")
	private int page = 1;

	/**
	 * 跳过开头记录数（基于0开始），也是指从数据集的第几条开始查询（如果大于0，优先级高于page）
	 */
	@ApiModelProperty(value = "跳转页")
	private int skip;
	/**
	 * 每页显示的条数
	 */
	@ApiModelProperty(value = "每页显示的条数")
	private int rows = Constant.DEFAULT_PAGE_SIZE;
	/**
	 * 总共的条数
	 */
	@ApiModelProperty(value = "总条数")
	private int total;
	/**
	 * 查询后返回的结果集
	 */
	@ApiModelProperty(value = "查询结果集")
	private List<T> queryList;

	/**
	 * （查询参数）获取每页记录数
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**（查询参数）设置每页记录数（-1表示查询所有记录）
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**（查询结果）获取总记录数
	 * @return
	 */
	public int getTotal() {
		if(total<=0)
		{
			if(queryList!=null)
				total=queryList.size();
		}
		return total;
	}

	/**
	 * （查询结果）设置总记录数
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * （查询参数）获取当前页号（基于1开始），如果同时也设置了skip(大于0)，则skip优先使用
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/** @see #getPage
	 * @param page 设置当前页号（基于1开始）
	 */
	public void setPage(int page)
	{
		if(page<=0) throw new IllegalArgumentException("页号从1开始计数");
		this.page = page;
	}

	/**
	 * （查询结果）获取果列表。总不为空
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getQueryList() {
		return queryList==null?Collections.EMPTY_LIST:queryList;
	}

	/**
	 * （查询结果）设置结果列表
	 * @param queryList
	 */
	public void setQueryList(List<T> queryList) {
		this.queryList = queryList;
	}

	/**
	 * （查询参数）跳过开头记录数（基于0开始），也是指从数据集的第几条开始查询（如果大于0，优先于page）
	 * @return
	 */
	public int getSkip() {
		return skip;
	}

	/** @see #getSkip
	 * @param skip 跳过的开头记录数（基于0开始）
	 */
	public void setSkip(int skip) {
		this.skip = skip;
	}

	/**
	 * （查询结果）返回组装的结果，兼容easyUI的分页功能, 可增加需要扩展返回值
	 * @see #getResult(int, List)
	 * @return
	 */
	@JsonIgnore
	public ResultDataset<T> getResult()
	{
		return PageEntity.getResult(this.getTotal(), this.getQueryList());
	}

	/**
	 * （查询结果）基于设置的参数进行内存分页，并返回分页后的结果。
	 * 根据skip（或page计算出）跳过指定数量的项，并返回rows数量的项。
	 * @param src 原始数据列表
	 * @param <TItem> 记录对象
	 * @return
	 */
	public <TItem> List<TItem> pageResult(List<TItem> src)
	{
		List<TItem> result=null;

		if(CheckUtil.isNullorEmpty(src)) {
			return result;
		}

		int total=src.size();
		//确定要跳过的记录数
		int curSkip=this.getSkip();
		if(curSkip<=0) {//小于0，则通过当前页号计算要跳过的记录数
			//如果页号不合理 或 每页记录数不合理，则不跳过
			if(this.getPage()<=1 || this.getRows()<0) curSkip=0;
			else curSkip=(this.getPage()-1)*this.getRows();
		}
		//确定返回结果的结束位置
		int endPos=curSkip+this.getRows();
		if(this.getRows()<0 || endPos>total) endPos=total;
		//分页（如果要跳过的记录数大于总记录数，则返回null）
		if(curSkip==0 && endPos==total) {
			result=src;
		}
		else if(curSkip<total) {
			result=src.subList(curSkip, endPos);
		}
		return result;
	}

	/**
	 * 返回组装的结果，兼容easyUI的分页功能
	 * @param size 返回结果总记录数，如果为负值，则从rows获取
	 * @param rows 当前页的内容
	 * @return “total”，总记录数；“rows”，当前返回结果记录集合
	 */
	public static <T> ResultDataset<T> getResult(int size,List<T> rows)
	{
		ResultDataset<T> result = new ResultDataset<>();
		if(size<0)
		{
			size=rows.size();
		}
		result.setTotal(size);
		//如果为null，不需要设置
		if(rows!=null){
			//(dennis)这里直接设置了对象，而不是result.getRows().addAll(rows)，
			//主要因为预期外部使用场景是读取，很少会修改。所以这样做可节约一点转移元素所带来的时间损耗
			result.setRows(rows);
		}
		return result;
	}
}