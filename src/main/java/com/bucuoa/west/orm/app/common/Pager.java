package com.bucuoa.west.orm.app.common;

import java.util.List;

public class Pager {

	private int curPage = 1; // 当前页
	private int pageSize = 10; // 每页多少行
	private int totalRow; // 共多少行
	private int start;// 当前页起始行
	private int end;// 结束行
	private int totalPage; // 共多少页

	private List<?> data;
	
	public Pager(int curPage)
	{
		setCurPage(curPage);
	}

	public Pager(int curPage,int pageSize)
	{
		setCurPage(curPage);
		this.pageSize = pageSize;
	}
	
	public void setData(List<?> data) {
		this.data = data;
	}

	private void setCurPage(int curPage) {
		if (curPage < 1) {
			curPage = 1;
		} else {
			start = pageSize * (curPage - 1);
		}
		end = start + pageSize > totalRow ? totalRow : start + pageSize;
		this.curPage = curPage;
	}

	public void setTotalRow(int totalRow) {
		totalPage = (totalRow + pageSize - 1) / pageSize;
		this.totalRow = totalRow;
		if (totalPage < curPage) {
			curPage = totalPage;
			start = pageSize * (curPage - 1);
			end = totalRow;
		}
		end = start + pageSize > totalRow ? totalRow : start + pageSize;
	}

	public int getCurPage() {
		return curPage;
	}
	
	public int getPrePage() {
		return curPage <= 1 ? 1 : curPage - 1;
	}
	
	public int getNextPage() {
		return curPage == this.totalPage ? curPage : curPage +1;
	}

	public int getStart() {
		return start;
	}

	public List<?> getData() {
		return data;
	}

	public int getEnd() {

		return end;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public int getTotalPage() {

		return this.totalPage;
	}

	public boolean isEnd()
	{
		return this.getTotalPage() == this.curPage;
	}
	
	public boolean isStart()
	{
		return this.curPage == 1;
	}
}