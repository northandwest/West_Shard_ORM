package com.bucuoa.west.orm.app.common;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("rawtypes")
public class WPage implements  Serializable {

	private static final long serialVersionUID = 20159200000000L;
	
	private int pageNo = 1;
	private int pageSize = 30;
	private int totalCount;
	private int totalPage;
	
	private int count;
	private int last;
	private List data;
	private boolean firstPage;//是否是第一页
	private boolean lastPage;//是否是最后一页
	private String funcName = "jumpPage"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
	
	private int first = 1;// 首页索引
//	private int last;// 尾页索引
	private int prev;// 上一页索引
	private int next;// 下一页索引
	private String funcParam = ""; // 函数的附加参数，第三个参数值。
	
	private int length = 8;// 显示页面长度
	private int slider = 1;// 前后显示页面长度
	
	private String message = "";
	
	public WPage(){
		
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	public boolean haveNextPage(){
		return pageNo<totalPage;
	}
	public boolean havePrevPage(){
		return pageNo>1;
	}
	public int getNextPageNo(){
		return pageNo+1;
	}
	public int getPrevPageNo(){
		return pageNo-1<1?1:(pageNo-1);
	}
	public int getPageNo() {
		return pageNo<=0?1:pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.totalPage = (totalCount+pageSize-1)/pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.totalPage = (totalCount+pageSize-1)/pageSize;
	}
	public int getTotalPage() {
		return totalPage<=0?1:totalPage;
	}
	
	public int getCurrentIndex(){
		return (pageNo - 1) * pageSize;
	}
	
	public String toSql()
	{
		int pstart = (pageNo - 1) * pageSize;
		int pend = pageSize;
		StringBuilder sql = new StringBuilder();
		sql.append(" limit ").append(pstart).append(",").append(pend);
		return sql.toString();
	}
	
	private void initialize(){
		
		//1
		this.first = 1;
		this.count = this.totalCount;
		this.last = (int)(count / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);
		
		if (this.count % this.pageSize != 0 || this.last == 0) {
			this.last++;
		}

		if (this.last < this.first) {
			this.last = this.first;
		}
		
		if (this.pageNo <= 1) {
			this.pageNo = this.first;
			this.firstPage=true;
		}

		if (this.pageNo >= this.last) {
			this.pageNo = this.last;
			this.lastPage=true;
		}

		if (this.pageNo < this.last - 1) {
			this.next = this.pageNo + 1;
		} else {
			this.next = this.last;
		}

		if (this.pageNo > 1) {
			this.prev = this.pageNo - 1;
		} else {
			this.prev = this.first;
		}
		
		//2
		if (this.pageNo < this.first) {// 如果当前页小于首页
			this.pageNo = this.first;
		}

		if (this.pageNo > this.last) {// 如果当前页大于尾页
			this.pageNo = this.last;
		}
		
	}
	
	@Override
	public String toString() {
		
		initialize();
		
		StringBuilder sb = new StringBuilder();
		
		if (pageNo == first) {// 如果是首页
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n");
		} else {
			sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+prev+","+pageSize+",'"+funcParam+"');\">&#171; 上一页</a></li>\n");
		}

		int begin = pageNo - (this.length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}

		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
			if (i < begin) {
				sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<li class=\"active\"><a href=\"javascript:\">" + (i + 1 - first)
						+ "</a></li>\n");
			} else {
				sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
		}

		if (last - end > slider) {
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
			end = last - slider;
		}

		for (int i = end + 1; i <= last; i++) {
			sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+",'"+funcParam+"');\">"
					+ (i + 1 - first) + "</a></li>\n");
		}

		if (pageNo == last) {
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n");
		} else {
			sb.append("<li><a href=\"javascript:\" onclick=\""+funcName+"("+next+","+pageSize+",'"+funcParam+"');\">"
					+ "下一页 &#187;</a></li>\n");
		}

		sb.append("<li class=\"disabled controls\"><a href=\"javascript:\">当前 ");
		sb.append("<input type=\"text\" value=\""+pageNo+"\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
		sb.append(funcName+"(this.value,"+pageSize+",'"+funcParam+"');\" onclick=\"this.select();\"/> / ");
		sb.append("<input type=\"text\" value=\""+pageSize+"\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)");
		sb.append(funcName+"("+pageNo+",this.value,'"+funcParam+"');\" onclick=\"this.select();\"/> 条，");
		sb.append("共 " + count + " 条"+(message!=null?message:"")+"</a></li>\n");

		sb.insert(0,"<ul>\n").append("</ul>\n");
		
		sb.append("<div style=\"clear:both;\"></div>");

//		sb.insert(0,"<div class=\"page\">\n").append("</div>\n");
		
		return sb.toString();
	}

}
