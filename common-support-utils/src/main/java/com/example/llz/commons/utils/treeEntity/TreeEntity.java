package com.example.llz.commons.utils.treeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.TreeSet;

/**
 * @apiDefine TreeEntity 树节点对象
 * @apiSuccess (TreeEntity) {String} id 节点id
 * @apiSuccess (TreeEntity) {String} text 显示文本
 * @apiSuccess (TreeEntity) {String} iconCls 显示节点图标className（前端使用）
 * @apiSuccess (TreeEntity) {String} state 显示状态（open、closed）
 * @apiSuccess (TreeEntity) {String} parentId 所在父节点id
 * @apiSuccess (TreeEntity) {int} sort 排序值
 * @apiSuccess (TreeEntity) {TreeEntity_list} children 子节点
 * @apiSuccess (TreeEntity) {object} attributes 扩展属性（依据调用而不同，见下扩展属性描述）
 *
 */

/**
 * 树节点对象，内部节点按如下规则排序：
 * 1）先按分类，值从小到大；若相同，再：
 * 2）按排序值，从小到大；若相同，再：
 * 3）按名称，字符排序；若相同，再：
 * 4）按id，字符排序
 * @author dennis
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeEntity implements Comparable<TreeEntity>,Serializable
{
	private static final long serialVersionUID = -8367908181559979913L;
	
	/**
	 * 节点收缩状态标识
	 */
	public static final String STATE_CLOSED = "closed";
	/**
	 * 节点展开状态标识
	 */
	public static final String STATE_OPEN = "open";
	
	/**
	 * 空图标的样式
	 */
	public static final String NULL_ICON="icon-blank";
	/**
	 * 文件夹图标标式
	 */
	public static final String FOLDER_ICON="tree-folder";
	/** 节点id */
	/** 节点id */
	private String id;
	/** 显示文本 */
	/** 节点显示文本 */
	private String text;

	/** 节点显示的图标 */
	private String iconCls;

	/** 节点状态（open\close） */
	private String state;

	/** 父节点标识值 */
	private String parentId;
	
	/** 分类值，用于排序 */
	private int catalog;
	
	/** 排序值 */
	private float sort;

	/** 用于内部标识，此节点是否已处理过 */
	@JsonIgnore
	private boolean load;

	/** 自定义属性 */
	private Object attributes;

	/**
	 * true：是节点
	 */
	private boolean node = true;

	/** 子节点集 */
	private TreeSet<TreeEntity> children;


	public TreeEntity()
	{
		children=new TreeSet<>();
	}

	public boolean getNode(){
		return node;
	}

	public void setNode(boolean node){
		this.node=node;
	}

	/**
	 * 构造节点
	 * @param sId 节点id
	 * @param sText 显示文本
	 * @param sParentId 父节点id
	 * @param iSort 排序值
	 * @param sIcon 节点图标样式定义（null表示默认图标，""表示使用空图标）
	 */
	public TreeEntity(String sId, String sText, String sParentId, float iSort, String sIcon)
	{
		this();
		id=sId;
		text=sText;
		parentId=sParentId;
		sort=iSort;
		if(sIcon!=null){
			if(sIcon.isEmpty()) {
				sIcon=NULL_ICON;
			}
			iconCls=sIcon;
		}
	}

	/**
	 * 排序方法，先按排序值排序，排序值相同，再按照标题，再比较id
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(TreeEntity o) {
		if (o == null) {
			return 1;
		}
		//如果是同一对象，返回0（用于移除）
		if (this == o) {
			return 0;
		}
		//注意：不能返回0，因为在TreeSet排序时，如果比较是0值时，会认为是在同一个位置放置元素（将会覆盖原来的元素）
		int ir=Integer.compare(this.catalog, o.catalog);
		if(ir==0) {
			ir=Float.compare(this.sort, o.sort);
		}
		//如果排序值相同，再比较标题
		if (ir==0) {
			String stext = this.text;
			if (stext == null) {
				stext = "";
			}
			ir = stext.compareTo(o.text == null ? "" : o.text);
		}
		if (ir == 0) {
			String stext = this.id;
			if (stext == null) {
				stext = "";
			}
			ir = stext.compareTo(o.id == null ? "" : o.id);
		}
		
		//(dennis)【注】不能返回0（相等）。如果返回0，会被认为两个对象相等，则只会保留一个
		return ir == 0 ? 1 : ir;
	}

	public static String getStateClosed() {
		return STATE_CLOSED;
	}

	public static String getStateOpen() {
		return STATE_OPEN;
	}

	public static String getNullIcon() {
		return NULL_ICON;
	}

	public static String getFolderIcon() {
		return FOLDER_ICON;
	}

	/**
	 * 节点id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 节点显示文本
	 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 节点显示的图标className（前端使用）
	 */
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * 节点默认状态（open、closed），
	 * 如果为open，且没有children属性，则展示为叶子节点；
	 * 如果为closed，且没有children属性，则在展开时会发送加载节点的请求(请求中会添加id参数【Form Data数据】，指明当前点击展开的节点)。
	 */
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 父节点标识值
	 */
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 排序值
	 */
	public float getSort() {
		return sort;
	}

	public void setSort(float sort) {
		this.sort = sort;
	}

	/**
	 * 用于内部标识，此节点是否已处理过
	 */
	public boolean isLoad() {
		return load;
	}

	public void setLoad(boolean load) {
		this.load = load;
	}

	/**
	 * 节点自定义属性
	 */
	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	/**
	 * 子节点集
	 */
	public TreeSet<TreeEntity> getChildren() {
		return children;
	}

	public void setChildren(TreeSet<TreeEntity> children) {
		if(children==null) {
			this.children.clear();
		} else {
			this.children = children;
		}
	}
}
