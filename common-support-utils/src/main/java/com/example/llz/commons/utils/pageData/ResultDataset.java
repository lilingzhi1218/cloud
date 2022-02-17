package com.example.llz.commons.utils.pageData;

import com.example.llz.commons.utils.ConvertUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.TypeMismatchException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据记录集。至少包含两个元素：<br>
 * total，按相同方式可获取到记录的总数量；<br>
 * rows，当前获取到的记录列表，List类型，其中每个元素是一条记录对象。
 * 
 * @param <T> 一条记录对象的类型
 * @author dennis
 *
 */
public class ResultDataset<T> extends HashMap<String, Object> implements Serializable
{
	private static final long serialVersionUID = 2415009322814868193L;

	/**
	 * 获取记录集合的Key名
	 */
	public static final String ROWS_KEY="rows";
	/**
	 * 获取总数的Key名
	 */
	public static final String TOTAL_KEY="total";

	public ResultDataset()
	{
		this(-1, null);
	}
	
	public ResultDataset(int totalSize, List<T> rows)
	{
		super();
		this.setTotal(totalSize);
		this.setRows(rows);
	}
	
	/**
	 * 获取数据记录集合，不会返回null。<br>
	 * 需要注意：如果T是DataRecord，在通过json转换后，内部元素并不是DataRecord类型，
	 * 因此在调用前使用{@link #jsonPostToDataRecord}转换一下。
	 * @return
	 */
	@JsonIgnore
	public List<T> getRows()
	{
		@SuppressWarnings("unchecked")
		List<T> rows=(List<T>)this.get(ROWS_KEY);
		if(rows==null) {
			rows=new ArrayList<T>();
			this.put(ROWS_KEY, rows);
		}
		return rows;
	}
	
	/**
	 * 设置一个内容为空的记录集
	 */
	public void emptyRows()
	{
		List<T> rows=getRows();
		if(!rows.isEmpty())
			rows.clear();
	}
	
	/**
	 * 添加一个元素
	 * @param rec
	 */
	public void addRow(T rec)
	{
		List<T> rows=getRows();
		rows.add(rec);
	}
	
	/**
	 * 如果T为DataRecord类型，在从json串转为本对象时，其元素类型将默认是LinkedHashMap（而不是DataRecord类型）。<br>
	 * 所以在转换后，调用其他成员方法之前，需要先调用此方法将内部元素从Map转为DataRecord。<br>
	 * 示例代码：<br>
	 * <pre>
	 * 	String json='{"total":100,"rows":[{"rid":"0303","STYPE":1},{"rid":"55","STYPE":10}]}';
	 * 	ResultDataset&lt;DataRecord> read=JsonUtil.jsonStringToObject(json,
	 * 		 new TypeReference&lt;ResultDataset&lt;DataRecord>>() {})
	 * 	  .jsonPostToDataRecord();
	 * 	List&lt;DataRecord> rows=read.getRows();
	 * </pre>
	 * @throws TypeMismatchException 如果T不是DataRecord或Map类型，将抛出类型不匹配异常
	 */
	@SuppressWarnings("unchecked")
	public ResultDataset<DataRecord> jsonPostToDataRecord() throws TypeMismatchException
	{
		List<T> rows=(List<T>)this.get(ROWS_KEY);
		if(rows==null || rows.isEmpty())
			return (ResultDataset<DataRecord>)this;
		
		Object val=null;
		for(int inx=0;inx<rows.size();++inx) {
			val=rows.get(inx);
			if(val!=null)
				break;
		}
		if(val==null || val instanceof DataRecord) {
			return (ResultDataset<DataRecord>)this;
		}
		
		if(val instanceof Map) {
			ResultDataset<DataRecord> resultThis=(ResultDataset<DataRecord>)this;
			List<DataRecord> realRows=new ArrayList<>(rows.size());
			for(int inx=0;inx<rows.size();++inx) {
				val=rows.get(inx);
				DataRecord rec=null;
				if(val!=null) {
					rec=new DataRecord();
					for(Entry<?, ?> ent:((Map<?,?>)val).entrySet()) {
						rec.put(ConvertUtil.getValue(ent.getKey(),(String)null),ent.getValue());
					}
				}
				realRows.add(rec);
			}
			resultThis.setRows(realRows);
			
			return resultThis;
		}else {
			throw new TypeMismatchException(val, DataRecord.class);
		}
	}
	
	/**
	 * 设置数据记录集合
	 * @param rows
	 */
	public void setRows(List<T> rows)
	{
		this.put(ROWS_KEY,rows);
	}
	
	/**
	 * 总记录数（用于分页）
	 */
	@JsonIgnore
	public int getTotal()
	{
		int total=ConvertUtil.getValue(this.get(TOTAL_KEY),-1);
		if(total<0) {
			@SuppressWarnings("unchecked")
			List<T> rows=(List<T>)this.get(ROWS_KEY);
			if(rows!=null) {
				total = rows.size();
				//setTotal(total);
			}
		}
		return total;
	}
	/**
	 * 设置记录总数，注意：不是rows的数量
	 * @param size
	 */
	public void setTotal(int size)
	{
		this.put(TOTAL_KEY, size);
	}
}
